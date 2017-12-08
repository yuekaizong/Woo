package kaizone.songmaya.haiercash.retrofit.service;

import kaizone.songmaya.haiercash.retrofit.annoation.NeedToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by yuelibiao on 2017/11/23.
 */

public class TokenService {

    public static final List<String> tokenUri = TokenService.getTokenAnnotationUri(ApiRepository.class);

    public static List getTokenAnnotationUri(Class clazz) {
        String classpath = clazz.getName();
        ArrayList tokens = new ArrayList();
        try {
            Method[] methods = Class.forName(classpath).getMethods();
            for (Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                String uri = null;
                boolean has_token = false;
                for (int i = 0; i < annotations.length; i++) {
                    Annotation annotation = annotations[i];
                    if (annotation instanceof GET) {
                        uri = ((GET) annotation).value();
                    }
                    if (annotation instanceof POST) {
                        uri = ((POST) annotation).value();
                    }
                    if (annotation instanceof PUT) {
                        uri = ((PUT) annotation).value();
                    }
                    if (annotation instanceof DELETE) {
                        uri = ((DELETE) annotation).value();
                    }

                    if (annotation instanceof NeedToken) {
                        has_token = true;
                    }
                }
                if (has_token) {
                    tokens.add(uri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokens;
    }

    public static boolean hasToken(String path) {
        for (String haiTokenUri : tokenUri) {
            if (path.contains(haiTokenUri)) {
                return true;
            }
        }
        return false;
    }

}
