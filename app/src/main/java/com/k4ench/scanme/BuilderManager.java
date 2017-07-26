package com.k4ench.scanme;

public class BuilderManager {

    private static int[] imageResources = new int[]{

            R.mipmap.user

    };
    private static int[] imageResources1 = new int[]{
        R.string.text1

    };

    private static int imageResourceIndex = 0;
    private static int imageResourceIndex1 = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }
    static int getImageResource1() {
        if (imageResourceIndex1 >= imageResources1.length) imageResourceIndex1 = 0;
        return imageResources1[imageResourceIndex1++];
    }

    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }
}
