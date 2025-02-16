# Step Deploy on docker

### Step Deploy on docker 
1. Go to the path of project
2. typing command on Powershell
3. docker build -t my-assignment-app . 
4. docker run --env-file .env -p 8080:8080 my-assignment-app

### Fix Error Timezone
1. open powershell ,run as administrator
2. run command cd 'C:\ProgramData\MySQL\MySQL Server 8.0\'
3. open ./my.ini
4. add "default-time-zone= +07:00" in file and save file
5. restart service mysql

### Document
 - Api spac  /resources/apispac