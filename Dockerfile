# Use Ubuntu as the base image
FROM ubuntu:20.04

# Set environment variables to avoid interactive prompts during installation
ENV DEBIAN_FRONTEND=noninteractive

# Update and install required packages
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    maven \
    wget \
    curl \
    unzip \
    postgresql-client \
    && rm -rf /var/lib/apt/lists/*

# Copy the Glassfish zip file from your local machine into the container
COPY glassfish-7.0.22.zip /tmp/

# Install Glassfish 7.0.22 (Eclipse Glassfish)
RUN unzip /tmp/glassfish-7.0.22.zip -d /opt \
    && rm /tmp/glassfish-7.0.22.zip

# Set environment variables for Java and Glassfish
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH=$JAVA_HOME/bin:$PATH
ENV GLASSFISH_HOME=/opt/glassfish7
ENV PATH=$GLASSFISH_HOME/bin:$PATH

# Set working directory
WORKDIR /app

# Expose default ports for Glassfish and PostgreSQL
EXPOSE 8080 4848

# Default command
CMD ["/opt/glassfish7/bin/asadmin", "start-domain", "-v"]