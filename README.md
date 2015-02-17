#nmid-headline
--------
[中文用户点这里！](note/readme_chs.md)

This is a android news client for CQUPT. And it's a material design demonstrate as well :)


####Features
* Full Material Design
* RESTful API Design
* Translucent mode supported for kitkat
* Local CSS used in webview
* Night mode supported

![screenshot](screenshot/screenshot.png)
####Download
[availible on myapp](http://sj.qq.com/myapp/detail.htm?apkName=cn.edu.cqupt.nmid.headline)


####Open Source Components
######maven dependencies

```
dependencies {
  compile fileTree(dir: 'libs', include: ['*.jar'])
  compile 'com.squareup.picasso:picasso:2.4.0'
  compile 'com.squareup.okhttp:okhttp-urlconnection:2.0.0'
  compile 'com.squareup.okhttp:okhttp:2.0.0'
  compile 'com.squareup.retrofit:retrofit:1.9.0'
  compile 'com.jakewharton:butterknife:6.0.0'
  compile 'com.android.support:recyclerview-v7:21.0.3'
  compile 'com.android.support:appcompat-v7:21.0.3'
  compile 'com.getbase:floatingactionbutton:1.5.1'
  compile 'com.android.support:cardview-v7:21.0.3'
  compile 'com.github.ksoichiro:android-observablescrollview:1.5.0'
  compile 'me.drakeet.materialdialog:library:1.2.2'
  compile project(':onekeyshare')
}
```

###### Widget And Drawable Resources

* [SwipeBackLayout](https://github.com/ikew0ng/SwipeBackLayout)
* [ProgressBarCircular](https://github.com/navasmdc/MaterialDesignLibrary)
* [ScrimInsetsFrameLayout,SlidingTabLayout,SlidingTabStrip](https://github.com/google/iosched/blob/master/android/src/main/java/com/google/samples/apps/iosched/ui/widget/ScrimInsetsFrameLayout.java)
* [SendCommentButton](https://github.com/frogermcs/InstaMaterial)
* [Some Icon Resources](https://github.com/xiprox/WaniKani-for-Android)

If your project is not listed in, please tell me :)


####Build Environment
Android Studio 1.1 or higher


####License
```
Copyright 2015 miao1007@gmail.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
