# Instructions to run the application

### Deliverables
| Deliverable | Description |
| ------ | ------ |
| `\sajid.khan\instructions.md(this file)` | Instructions to be followed to run server and use it |
| `\sajid.khan\video-server-api-2.0-spec.yaml` | 2.0 openApi specification(for additional tasks) |
| `\sajid.khan\rest-challenge-network.tar` | server docker image as TAR to start in mode1 |
| `\sajid.khan\vide0-server-0.0.1.jar` | server binaries as JAR to start in mode2 |
| `\sajid.khan\client\video-client.jar` | client binaries to invoke the calls as CLI |
| `\sajid.khan\client\dependency-jars\picocli-4.6.3.jar` | Dependent jars to run video-client CLI|

### Donwload `sajid.khan(drive link)` to `Desktop` before next steps

### Installation (Server) -Terminal 1
#### Mode1 : Running as standalone(without docker)
- Open Command line and execute below scripts
  ```sh
     cd Desktop/sajid.khan
     java -jar video-server-0.0.1.jar
    ```

#### Mode2 : Running as container(with docker)
- Complete installation following Installation guide for Docker Descktop (skip if docker already exists on machine) https://www.docker.com/products/docker-desktop/
- Execute below scripts once docker is installed and configured(skip if docker already exists on machine)
```sh
     cd Desktop/sajid.khan
     docker load --input restapi-challenge-network.tar
     docker volume create storage
     docker run -p 8080:8080 --mount source=storage,target=/var/lib/data restapi-challenge-network:1
```

### Installation (Client) - Terminal 2
- Open Command line and execute below scripts
  ```sh
     cd Desktop/sajid.khan/client
     alias video-store='java -jar video-client.jar'
     video-store list
    ```
- Kindly take a look at below command tables to understand the options available and

### CLI Commands
| Command | Description |Operation |
| ------ | ------ |------|
|  `upload "source-path-of-file-with-extension"` |  |Upload file to Server|
|  `list ` | List files from server |List files from server| List files from server|
| `download "{fileId}"` | `fileId` is the unique identifier for files, returned from `list` operations |Download file from server|
| `delete "{fileId}"` | `fileId` is the unique identifier for files, returned from `list` operations |Delete file from server  |
| `-v` | Identifies the version of API to be called, its optional. If its not passed as argument, `v1` api is invoked as default  |  |
| `-u {user} -p` | If `-v` is passed as `v2` then theae set of parameter are required to and then password would be prompted to proceed further execution of the call   | Authorizes the user to perform operations on `v2`. User name and password is set as `admin` |
| `search` | With this option multiple parameters can be passed as filters to perform search operations, it can have fields as `name`, `sizeUpto` and `videoType`. None of them are mandatory |Search files based on filters|


### Some examples
| Command | Result |
| ------ | ------ |
| `video-store list` | `200` is returned with no files data as there does not exist any file at the moment|
| `video-store upload "/Users/sajid.khan/Desktop/test.mp4"` |`201` is returned as `test.mp4` is uploaded on server and returns the location of the uploaded file|
| `video-store upload "/Users/sajid.khan/Desktop/test.mp4"` |`409` is returned as `test.mp4` already exists on server|
| `video-store download "dGVzdC5tcDQ"` |`200` is retuned and File is downloaded successfully to the user direcotry i.e `~/sajid.khan/client/test.mp4`|
| `video-store delete "dGVzdC5tcDQ"` |`204` is retuned. File is deleted successfully|
| `video-store delete "dGVzdC5tcDQ"` |`404` returned as the same file is already deleted|
| `video-store search name=test search videoType=mp4` |`200` returns the file whose name contains `test` and video type is `mp4`|
| `video-store -v "v2" -u admin -p upload "/Users/sajid.khan/Desktop/test.mp4"` |`201` is returned as `test.mp4` created and since the request was for v2, hence authentication input was required|
