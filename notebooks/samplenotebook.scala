// Databricks notebook source
## Session configuration

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