# arcdaioappmange
for managing Arcadio Apps

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        compile 'com.github.rafiur:arcdaioappmange:111'
	}
  
  ArcAppManager.getInstance().initiate(this, new HttpSyncAppManager.onHttpSyncNotifyListener() {
            @Override
            public void onPreConnection() {

            }

            @Override
            public void onPostConnection(Object o, boolean b) {
                if (b) {
                    try {
                        ArcAppManager.getInstance().showPromotedAds(MainActivity.this, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public String onDoInBackground(String s) {
                return s + "com.cynomusic.mp3downloader";
            }
        }, 1);
    }
