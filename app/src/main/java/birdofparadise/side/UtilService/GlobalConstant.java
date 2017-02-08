package birdofparadise.side.UtilService;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

public class GlobalConstant {
    private static GlobalConstant ourInstance = new GlobalConstant();

    public static GlobalConstant getInstance() {
        return ourInstance;
    }

    private GlobalConstant() {
    }


    public String getFragmentMessageConatinerKey() {
        return "fragmentMessageConatinerKey";
    }

}
