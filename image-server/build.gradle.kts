plugins {
    `java`
    `application`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.2.2")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("com.amazonaws:aws-lambda-java-log4j2:1.5.1")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.200")
    implementation("com.amazonaws:aws-java-sdk-dynamodb:1.12.200")

    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    testCompileOnly("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.22")
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
