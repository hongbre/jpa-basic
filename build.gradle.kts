plugins {
    id("java")
}

group = "jpa-basic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //spring-boot-starter-jpa 를 보통 많이 쓴다고 하지만,
    //강의에서는 maven 에 hibernate 와 h2 database 를 따로 추가했다.
    implementation("org.hibernate.orm:hibernate-core:7.0.6.Final") //hibernate
    implementation("com.h2database:h2:2.3.232") //h2 database

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}