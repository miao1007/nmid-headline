#APP在线更新模块

##检测更新 [GET /checkforupdate]
APP的用户在启动时上传当前APP的版本号与APP的包名，查询APK当前是否更新到最新版本，如果有可用的更新，则返回最新的下载地址，如果没有的话，返回404.

####Parameters
|Name |Description | Details
|:|
|applicationid|程序的包名 | String,not null,eg,"cn.edu.cqupt.nmid.headline"
|versionname|版本信息| String,not null,eg,"1.2"

####Return

+ Response 200 (minetype : application/vnd.android.package-archive)

```
xxxx.apk
```

+ Response 404


##更新当前程序包 [POST /uploadlatestedapp]
开发者在后台上传最新的apk到服务器，服务器将立即解析APK，并判断当前上传的apk版本是否比服务器中存储的APK新，如果比服务器新的话，则返回status=0，反之返回status=1。

####Parameters
|Name |Description | Details
|:|
|file|将要上传的APK文件| File,not null,mine type = "application/vnd.android.package-archive"
<!--|applicationid|程序的包名 | String,not null
|versionname|版本信息| String,not null-->

####Return
上传成功，且解析后的版本发现比服务器高

+ Response 200

```
{status=0,applicationid=$applicationid,versionname=$versionname}
```
上传成功，但是解析后的版本发现比服务器低

+ Response 200

```
{status=1}
```
上传失败

+ Response 500 

```
Server Parse Error!
```

