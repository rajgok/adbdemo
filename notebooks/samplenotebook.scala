// Databricks notebook source
val appID = "4883b6ef-201c-49d0-8505-e94f64668d29"
val password = "5FkrM1wexR0aY_kzeP:Pa6r+cHCEFGz/"
val fileSystemName = "rakanc-fsname"
val tenantID = "485690d2-5822-4013-a363-9ea3952187e6"

spark.conf.set("fs.azure.account.auth.type", "OAuth")
spark.conf.set("fs.azure.account.oauth.provider.type", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider")
spark.conf.set("fs.azure.account.oauth2.client.id", appID)
spark.conf.set("fs.azure.account.oauth2.client.secret", password)
spark.conf.set("fs.azure.account.oauth2.client.endpoint", "https://login.microsoftonline.com/485690d2-5822-4013-a363-9ea3952187e6/oauth2/token")
spark.conf.set("fs.azure.createRemoteFileSystemDuringInitialization", "true")
dbutils.fs.ls("abfss://rakanc-fsname@adlgen2sa.dfs.core.windows.net/")
spark.conf.set("fs.azure.createRemoteFileSystemDuringInitialization", "false")

// COMMAND ----------

val storageAccountName = "adlgen2sa"
val appID = "4883b6ef-201c-49d0-8505-e94f64668d29"
val password = "5FkrM1wexR0aY_kzeP:Pa6r+cHCEFGz/"
val fileSystemName = "rakanc-fsname"
val tenantID = "485690d2-5822-4013-a363-9ea3952187e6"

spark.conf.set("fs.azure.account.auth.type." + storageAccountName + ".dfs.core.windows.net", "OAuth")
spark.conf.set("fs.azure.account.oauth.provider.type." + storageAccountName + ".dfs.core.windows.net", "org.apache.hadoop.fs.azurebfs.oauth2.ClientCredsTokenProvider")
spark.conf.set("fs.azure.account.oauth2.client.id." + storageAccountName + ".dfs.core.windows.net", "" + appID + "")
spark.conf.set("fs.azure.account.oauth2.client.secret." + storageAccountName + ".dfs.core.windows.net", "" + password + "")
spark.conf.set("fs.azure.account.oauth2.client.endpoint." + storageAccountName + ".dfs.core.windows.net", "https://login.microsoftonline.com/" + tenantID + "/oauth2/token")
spark.conf.set("fs.azure.createRemoteFileSystemDuringInitialization", "true")
dbutils.fs.ls("abfss://" + fileSystemName  + "@" + storageAccountName + ".dfs.core.windows.net/")
spark.conf.set("fs.azure.createRemoteFileSystemDuringInitialization", "false")

// COMMAND ----------

// MAGIC %sh wget -P /tmp https://raw.githubusercontent.com/Azure/usql/master/Examples/Samples/Data/json/radiowebsite/small_radio_json.json

// COMMAND ----------

dbutils.fs.cp("file:///tmp/small_radio_json.json", "abfss://" + fileSystemName + "@" + storageAccountName + ".dfs.core.windows.net/")

// COMMAND ----------

val df = spark.read.json("abfss://"+ fileSystemName + "@" + storageAccountName + ".dfs.core.windows.net/small_radio_json.json")


// COMMAND ----------

val specificColumnsDf = df.select("firstname", "lastname", "gender", "location", "level")
specificColumnsDf.show()

// COMMAND ----------

val renamedColumnsDF = specificColumnsDf.withColumnRenamed("level", "subscription_type")
renamedColumnsDF.show()

// COMMAND ----------

val blobStorage = "rakancsa.blob.core.windows.net"
val blobContainer = "rakancsa"
val blobAccessKey =  "b02ncGtrKH79UgJUv1C1Kd+hn5Zu7VUKSBK0jGiGSY+LK0d1HEl6xfJ6vGy8aVbxVSzLcdcpdyWomg1wV5ffBQ=="

// COMMAND ----------

val tempDir = "wasbs://" + blobContainer + "@" + blobStorage +"/tempDirs"
val acntInfo = "fs.azure.account.key."+ blobStorage
sc.hadoopConfiguration.set(acntInfo, blobAccessKey)

// COMMAND ----------

//SQL Data Warehouse related settings
val dwDatabase = "rakancdWH"
val dwServer = "rakanc.database.windows.net"
val dwUser = "adminlogin"
val dwPass = "admin@123"
val dwJdbcPort =  "1433"
val dwJdbcExtraOptions = "encrypt=true;trustServerCertificate=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"
val sqlDwUrl = "jdbc:sqlserver://" + dwServer + ":" + dwJdbcPort + ";database=" + dwDatabase + ";user=" + dwUser+";password=" + dwPass + ";$dwJdbcExtraOptions"
val sqlDwUrlSmall = "jdbc:sqlserver://" + dwServer + ":" + dwJdbcPort + ";database=" + dwDatabase + ";user=" + dwUser+";password=" + dwPass