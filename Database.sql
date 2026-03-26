CREATE DATABASE TimeBankDB;
GO

USE TimeBankDB;
GO

-- 1. Bảng Users
CREATE TABLE Users (
    user_id INT IDENTITY PRIMARY KEY,
    full_name NVARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    phone VARCHAR(20),
    cccd VARCHAR(20),
    address NVARCHAR(200),
    avatar VARCHAR(255),
    wallet_hours FLOAT DEFAULT 0
);
GO

-- 2. Bảng Services (bài đăng)
CREATE TABLE Services (
    service_id INT IDENTITY PRIMARY KEY,
    user_id INT,
    title NVARCHAR(200),
    description NVARCHAR(MAX),
    hours_required FLOAT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
GO

-- 3. Bảng Booking (đặt lịch)
CREATE TABLE Bookings (
    booking_id INT IDENTITY PRIMARY KEY,
    service_id INT,
    requester_id INT,
    provider_id INT,
    schedule_time DATETIME,
    status VARCHAR(50), -- pending, completed
    FOREIGN KEY (service_id) REFERENCES Services(service_id),
    FOREIGN KEY (requester_id) REFERENCES Users(user_id),
    FOREIGN KEY (provider_id) REFERENCES Users(user_id)
);
GO

-- 4. Bảng Transactions (ví thời gian)
CREATE TABLE Transactions (
    trans_id INT IDENTITY PRIMARY KEY,
    from_user INT,
    to_user INT,
    hours FLOAT,
    created_at DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (from_user) REFERENCES Users(user_id),
    FOREIGN KEY (to_user) REFERENCES Users(user_id)
);
GO

-- 5. Bảng Reviews
CREATE TABLE Reviews (
    review_id INT IDENTITY PRIMARY KEY,
    booking_id INT,
    rating INT,
    comment NVARCHAR(300),
    FOREIGN KEY (booking_id) REFERENCES Bookings(booking_id)
);
GO

-- 6. Bảng Messages (chat)
CREATE TABLE Messages (
    msg_id INT IDENTITY PRIMARY KEY,
    sender_id INT,
    receiver_id INT,
    content NVARCHAR(MAX),
    sent_at DATETIME DEFAULT GETDATE(),
    is_read BIT DEFAULT 0,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id)
);
GO

-- 7. Thêm dữ liệu mẫu
INSERT INTO Users (full_name, email, password, phone, cccd, address, avatar, wallet_hours)
VALUES
(N'Lâm Khánh Vy', 'a@gmail.com', '123', '0901111111', '123456789', N'Đà Nẵng', 'avatar1.jpg', 5),
(N'Trần Thị Nhàn', 'b@gmail.com', '123', '0902222222', '987654321', N'Hà Nội', 'avatar2.jpg', 3),
(N'Lê Văn Sĩ', 'c@gmail.com', '123', '0903333333', '456123789', N'TP.HCM', 'avatar3.jpg', 2),
(N'Phạm Thị Duyên', 'd@gmail.com', '123', '0904444444', '159753456', N'Đà Nẵng', 'avatar4.jpg', 4),
(N'Quản Trị Hệ Thống (Admin)', 'admin@timebank.com', 'adminpass', '19001560', '000000', N'HQ', 'default_avatar.png', 0);
GO

INSERT INTO Services (user_id, title, description, hours_required)
VALUES
(1, N'Dạy tiếng Trung cơ bản', N'Học giao tiếp cơ bản trong 1 giờ', 1),
(2, N'Sửa laptop', N'Fix lỗi phần mềm, cài win', 2),
(3, N'Chỉnh sửa ảnh', N'Photoshop chuyên nghiệp', 1.5),
(4, N'Dạy Excel', N'Từ cơ bản đến nâng cao', 2),
(1, N'Dạy Java cơ bản', N'Học OOP trong 2 giờ', 2);
GO

INSERT INTO Bookings (service_id, requester_id, provider_id, schedule_time, status)
VALUES
(1, 2, 1, '2026-03-21 10:00:00', 'completed'),
(2, 1, 2, '2026-03-22 14:00:00', 'pending'),
(3, 4, 3, '2026-03-23 09:00:00', 'completed'),
(4, 3, 4, '2026-03-24 15:00:00', 'pending');
GO

INSERT INTO Transactions (from_user, to_user, hours)
VALUES
(2, 1, 1),
(4, 3, 1.5);
GO

INSERT INTO Reviews (booking_id, rating, comment)
VALUES
(1, 5, N'Dạy rất dễ hiểu, nhiệt tình'),
(3, 4, N'Chỉnh ảnh đẹp nhưng hơi chậm');
GO

INSERT INTO Messages (sender_id, receiver_id, content)
VALUES
(1, 2, N'Chào bạn, bạn rảnh không?'),
(2, 1, N'Ok, mai học nhé'),
(3, 4, N'Bạn cần chỉnh ảnh gì?'),
(4, 3, N'Chỉnh ảnh chân dung');
GO

-- =====================================================
-- FIX: Đảm bảo tài khoản admin luôn tồn tại
-- Chạy đoạn này nếu không đăng nhập được admin
-- =====================================================
IF NOT EXISTS (SELECT 1 FROM Users WHERE email = 'admin@timebank.com')
BEGIN
    INSERT INTO Users (full_name, email, password, phone, cccd, address, avatar, wallet_hours)
    VALUES (N'Quản Trị Hệ Thống (Admin)', 'admin@timebank.com', 'adminpass', '19001560', '000000', N'HQ', 'default_avatar.png', 0)
END
ELSE
BEGIN
    UPDATE Users SET password = 'adminpass' WHERE email = 'admin@timebank.com'
END
GO

SELECT user_id, full_name, email, password FROM Users WHERE email = 'admin@timebank.com';
GO
