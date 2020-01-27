![Codecov](https://img.shields.io/codecov/c/github/welldsagl/kogito-kjar-maven-plugin?token=a5a71dcc831b476db46988222a05846a)
![GitHub Workflow Status](https://img.shields.io/github/workflow/status/welldsagl/kogito-kjar-maven-plugin/master)

# WellD Kogito KJAR Maven plugin

This plugin can be used to copy and, if necessary, convert the files composing the Drools knowledge.

## Why does this exist?

At the time of writing, [Kogito](https://kogito.kie.org/) does not support loading knowledge from a kjar dependency.
Furthermore, it is unable of understanding *Guided Decision Table* (`.gdst`) files or *Guided Template Rules*
(`.template`) files.
This plugin provides a workaround by copying files to a Kogito `resources` directory at compile time, and converting
all `.gdst` and `.template` files to `.drl` files.

## How it works

The plugin looks into the chosen input directory (parameter `inputDirectory`) for Drools knowledge files.

 - For each `.gdst` or `.template` file the plugin creates a corresponding drl translation in the 
 chosen output directory (`outputDirectory`).
 - `.drl` files are simply copied into the output directory.

Folder structure is preserved after the copy, for instance if the input directory is `source` and has the following structure
 
 ```bash
    myproject/
    └── source/
        ├── dogs.gdst
        ├── cats.drl
        └── others/
            └── birds.gdst
```

the output directory `target` will preserve the relative position of every file after executing the plugin

```bash
    myproject/
    └── target/
        ├── dogs.drl
        ├── cats.drl
        └── others/
            └── birds.drl
```

The `gdst-to-drl` conversion leverages the Drools Java API provided by the `drools-workbench-models-guided-dtable` library.

## Basic usage

In order to activate the knowledge conversion and copy, you have to add the plugin to the `build/plugins` section
of your project's `pom.xml`.

```xml
<plugin>
    <groupId>ch.welld.kie</groupId>
    <artifactId>welld-kogito-kjar-maven-plugin</artifactId>
    <version>1.1.0-SNAPSHOT</version>
    <executions>
      <execution>
        <id>knowledgeconversion</id>
        <phase>generate-resources</phase>
        <goals>
          <goal>convert-knowledge</goal>
        </goals>
        <configuration>
          <inputDirectory>./drools</inputDirectory>
          <outputDirectory>./src/main/resources</outputDirectory>
          <overwriteFiles>true</overwriteFiles>
        </configuration>
      </execution>
    </executions>
</plugin>
```

## Parameters

The plugin supports the following parameters:

 Parameter        | Type    | Default | Description                                                                   
 ---------------- | ------- | ------- | -----------
 inputDirectory   | String  | -       | The directory where drools knowledge files are read from. REQUIRED parameter. 
 outputDirectory  | String  | -       | The directory where drools knowledge files are saved. REQUIRED parameter.     
 overwriteFiles   | boolean | false   | If true existing files in the output directory will be overridden by the new ones, otherwise old ones will be preserved.

## Complete usage example

1. Configure `maven-dependency-plugin` to extract knowledge files from a dependency to a temporary directory;
2. Configure `welld-drools-maven-plugin` to copy and convert files from the temporary directory;
3. Use `maven-clean-plugin` to cleanup the copied files in the `clean` phase.

The temporary directory is not deleted, so you may want to add it 
(together with the output directory) to your `.gitignore`.

```xml
<build>
    <plugins>
        <plugin>
            <artifactId>maven-clean-plugin</artifactId>
            <version>3.1.0</version>
            <configuration>
              <filesets>
                <fileset><directory>drools</directory></fileset>
                <fileset><directory>src/main/resources/drools</directory></fileset>
              </filesets>
            </configuration>
          </plugin>
          <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-dependency-plugin</artifactId>
            <version>3.1.1</version>
            <executions>
              <execution>
                <id>unpack</id>
                <phase>generate-resources</phase>
                <goals>
                  <goal>unpack</goal>
                </goals>
                <configuration>
                  <artifactItems>
                    <artifactItem>
                      <groupId>ch.welld.schindler.fixture</groupId>
                      <artifactId>droolsknowledge</artifactId>
                      <outputDirectory>./drools</outputDirectory>
                      <includes>**/*.drl,**/*.gdst,,**/*.template</includes>
                    </artifactItem>
                  </artifactItems>
                  <overWriteReleases>true</overWriteReleases>
                </configuration>
              </execution>
            </executions>
          </plugin>
          <plugin>
            <groupId>ch.welld.kie</groupId>
            <artifactId>welld-kogito-kjar-maven-plugin</artifactId>
            <version>1.1.0-SNAPSHOT</version>
            <executions>
              <execution>
                <id>knowledge-conversion</id>
                <phase>generate-resources</phase>
                <goals>
                  <goal>convert-knowledge</goal>
                </goals>
                <configuration>
                  <inputDirectory>./drools</inputDirectory>
                  <outputDirectory>./src/main/resources/drools</outputDirectory>
                  <overwriteFiles>true</overwriteFiles>
                </configuration>
              </execution>
            </executions>
          </plugin>
    </plugins>
</build>
```

## Known limitations
 
**WARNING:** Be careful with input files with same name but different extension because only one of them will be saved into the
output directory. We plan to fix this problem eventually.
 
 