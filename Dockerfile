FROM tomcat:9.0-jdk11-openjdk-slim

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the built .war file to Tomcat
# Note: Ensure you run 'Clean and Build' in NetBeans first so this file exists in dist/
COPY dist/TimeBank.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
