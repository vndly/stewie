# Swagger Generator
A Java library that generates a Swagger configuration file based on the declared API.

## Configuration

Add the following code to your **pom.xml**:

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>com.github.mauriciotogneri</groupId>
                <artifactId>swagger</artifactId>
                <version>1.0.0</version>
            </plugin>
        </plugins>
    </build>
    
    <pluginRepositories>
        <pluginRepository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </pluginRepository>
    </pluginRepositories>
```