# Drools knowledge Maven plugin

This plugin can be used to copy and, if necessary, convert the files composing the Drools knowledge.
By now, we treat
 - `GDST` files: they are translated into `DRL` files (same file name) and copied into the chosen output directory
 - `DRL` files
 

## How it works

The plugin looks into the chosen input directory (parameter `inputDirectory`) for Drools knowledge files.

 - For each `.gdst` file the plugin creates a corresponding drl translation in the chosen output directory (`outputDirectory`).
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

The `gdst-to-drl` conversion happens thanks to the Drools Java API provided by the `drools-workbench-models-guided-dtable` library.

## Usage

In order to activate the knowledge conversion and copy, you have to add the Welld Drools Maven Plugin to the `build/plugins` section
of your project's `pom.xml`.

```xml
<plugin>
    <groupId>ch.welld.drools</groupId>
    <artifactId>welld-drools-maven-plugin</artifactId>
    <version>1.0</version>
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
          <overrideFiles>true</overrideFiles>
        </configuration>
      </execution>
    </executions>
</plugin>
```

The plugin supports the following parameters:

 Parameter       | Type    | Default | Description                                                                   
 --------------- | ------- | ------- | -----------
 inputDirectory  | String  | -       | The directory where drools knowledge files are read from. REQUIRED parameter. 
 outputDirectory | String  | -       | The directory where drools knowledge files are saved. REQUIRED parameter.     
 overrideFiles   | boolean | false   | If true existing files in the output directory will be overridden by the new ones, otherwise old ones will be preserved.
 
Be careful with input files with same name but different extension because only one of them will be saved into the
output directory.
 
 