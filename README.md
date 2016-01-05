# GitHubStarred
This application show logged user's starred repository on Github in RecyclerView.
In this project, you can see how to get JSON Data via Github API and parsing it with Android Retrofit library.
After parsing data showing it in RecyclerView. When you clicked the spesific repository which listed in Home Page, you can see the 
percentages based on Forks and Stars Count and you can share that statistics.

##Prerequsities

You should have build.gradle looking like this :

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.1.0'
    compile 'com.android.support:design:23.1.0'
    compile 'com.google.code.gson:gson:2.3'
    compile 'org.parceler:parceler:0.2.13'
    compile 'com.squareup.retrofit:retrofit:1.9.0'
    compile 'com.android.support:cardview-v7:23.0.+'
    compile 'com.android.support:recyclerview-v7:23.0.+'
    compile 'com.github.bumptech.glide:glide:3.6.1'
    compile 'com.android.support:support-v4:23.0.+'
    compile 'de.hdodenhof:circleimageview:1.2.1'
}
```

