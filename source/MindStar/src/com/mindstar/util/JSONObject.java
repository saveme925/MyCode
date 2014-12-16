package com.mindstar.util;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

/**
 *  Helper Class to convert any object to JSON formatted string.
 *
 */
public class JSONObject {
 
	
	//--------------------------------------------------------------------------
    /**
     *  Private variable declaration.
     */
    private HashMap objectMap    =   null;
    private SimpleDateFormat	javaDateFormat	=	null;
    //--------------------------------------------------------------------------
    /** 
     * Creates a new instance of JSONObject 
     */
    public JSONObject() {
        objectMap    	=   new HashMap();
        javaDateFormat	=	new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
    }
    
    //--------------------------------------------------------------------------
    public JSONObject(JSONizable p_jsonizable_obj) {
    	this(p_jsonizable_obj.toJSON());
    }
    
    //--------------------------------------------------------------------------
    public JSONObject(Map p_json_map) {
    	this();
    	if(p_json_map == null) {
    		return;
    	}
    	for(Object l_key : p_json_map.keySet()) {
    		addParam(l_key.toString(), p_json_map.get(l_key));
    	}
    }
    //--------------------------------------------------------------------------
    /**
     *  Method to add key value pair to the JSON Object.
     *  
     *  @param  p_key:String    The key for the parameter to be added.
     *  @param  p_value:Object  The value for the parameter to be added.
     */
    @SuppressWarnings("unchecked")
    public void addParam(String p_key,Object p_value){
    	
        if(p_value == null){
        	objectMap.put(p_key,org.json.JSONObject.NULL);
            return;
        }
        if(p_value instanceof byte[]) {
        	System.out.println("Ignoring " + p_key + " as it is a byte array");
        	return;
        }
        
        if(p_value instanceof JSONizable) {
        	objectMap.put(p_key, new JSONObject((JSONizable) p_value));
            return;
        }
        if(p_value instanceof Collection) {
        	objectMap.put(p_key, JSONHelper.convertArrayToJSON(((Collection) p_value).toArray()));
        	return;
        }
        if(p_value instanceof Map) {
        	objectMap.put(p_key, (new JSONObject((Map) p_value)).toJSON());
        	return;
        }
        if(p_value instanceof String){
        	if(((String)p_value).trim().length() == 0){
        		objectMap.put(p_key, org.json.JSONObject.NULL);
        		return;
        	}
        }
        if(p_value.getClass().isArray()) {
        	objectMap.put(p_key, JSONHelper.convertArrayToJSON((Object []) p_value));
        }
        
        objectMap.put(p_key,p_value.toString());
    }
    //--------------------------------------------------------------------------
    /**
     *  Method to add key value pair to the JSON Object.
     *  
     *  @param  p_key:String    The key for the parameter to be added.
     *  @param  p_map:Map       The value for the parameter to be added.
     */
    @SuppressWarnings("unchecked")
	public void addParam(String p_key,Map p_map){
        org.json.JSONObject l_object =  convertMapToJSON(p_map);
        if(l_object == null){
            objectMap.put(p_key,org.json.JSONObject.NULL);
            return;
        }
        objectMap.put(p_key,l_object);
    }
    //--------------------------------------------------------------------------
    /**
     *  Method to add key value pair to the JSON Object.
     *  
     *  @param  p_key:String             The key for the parameter to be added.
     *  @param  p_collection:Collection  The value for the parameter to be added.
     */
    @SuppressWarnings("unchecked")
	public void addParam(String p_key,Collection p_collection){
        JSONArray l_object =  JSONHelper.convertArrayToJSON(p_collection.toArray());
        if(l_object == null){
            objectMap.put(p_key,org.json.JSONObject.NULL);
            return;
        }
        objectMap.put(p_key,l_object);
    }
    //--------------------------------------------------------------------------
    /**
     *  Method to add key value pair to the JSON Object.
     *  
     *  @param  p_key:String        The key for the parameter to be added.
     *  @param  p_array:Object[]    The value for the parameter to be added.
     */
    @SuppressWarnings("unchecked")
	public void addParam(String p_key,Object[] p_array){
        JSONArray l_object =  convertArrayToJSON(p_array);
        if(l_object == null){
            objectMap.put(p_key,org.json.JSONObject.NULL);
            return;
        }
        objectMap.put(p_key,l_object);
    }
    //--------------------------------------------------------------------------
    /**
     *  Recursive method to convert the Collection to JSON Array.
     *  @param  p_collection:Collection Collection to be converted to JSON Array.
     *  @return JSONArray               Converted JSON Array
     */
    public static JSONArray convertCollectionToJSON(Collection p_collection){
        if(p_collection == null){
            return null;
        }
        return convertArrayToJSON(p_collection.toArray());
    } 
    
    //--------------------------------------------------------------------------
    /**
     *  Recursive method to convert the Object[] to JSON Array.
     *  @param  p_array:Object[]    Object[] to be converted to JSON Array.
     *  @return JSONArray           Converted JSON Array
     */
    public static JSONArray convertArrayToJSON(Object[] p_array){
        if(p_array == null){
            return null;
        }
        JSONArray  l_object     =   null;
        int        l_size       =   p_array.length;
        
        l_object    =   new JSONArray();
        
        for(int l_i = 0;l_i < l_size; l_i++){
            Object l_value  =   p_array[l_i];
            if(l_value == null){
                l_object.put(org.json.JSONObject.NULL);
                continue;
            }
            if(l_value instanceof Map){
                l_object.put(convertMapToJSON((Map)l_value));
            }else if(l_value instanceof Collection){
                l_object.put(convertCollectionToJSON((Collection)l_value));
            }else if(l_value instanceof Object[]){
                l_object.put(convertArrayToJSON((Object[])l_value));
            }else if (l_value instanceof JSONizable){
                l_object.put(((JSONizable)l_value).toJSON());
            }else{
                l_object.put(l_value.toString());
            }
        }
        
        System.out.println("Returning json array " + l_object);
        
        return l_object;
    }
    //--------------------------------------------------------------------------
    /**
     *  Recursive method to convert the Map to org.json.JSONObject.
     *  @param  p_map:Map              Map to be converted to org.json.JSONObject.
     *  @return org.json.JSONObject    Converted org.json.JSONObject
     */
    @SuppressWarnings("unchecked")
	public static org.json.JSONObject convertMapToJSON(Map p_map){
        if(p_map == null){
            return null;
        }
        org.json.JSONObject  l_object    =   null;
        Map         l_map       =   new HashMap();
        for(Object l_key:p_map.keySet()){
            Object  l_value =   p_map.get(l_key);
            if(l_value == null){
                l_map.put(l_key, org.json.JSONObject.NULL);
                continue;
            }
            if(l_value instanceof Map){
                l_map.put(l_key,convertMapToJSON((Map)l_value));
            }else if(l_value instanceof Collection){
                l_map.put(l_key,convertCollectionToJSON((Collection)l_value));
            }else if(l_value instanceof Object[]){
                l_map.put(l_key,convertArrayToJSON((Object[])l_value));
            }else if (l_value instanceof JSONizable){
                l_map.put(l_key,((JSONizable)l_value).toJSON());
            }else{
                l_map.put(l_key,l_value.toString());
            }
        }

        l_object    =   new org.json.JSONObject(l_map);
        return l_object;
    }
    
    //--------------------------------------------------------------------------
    public org.json.JSONObject toJSON() {
    	return new org.json.JSONObject(objectMap);
    }
    
    //--------------------------------------------------------------------------
    /**
     *  Overriding toString method to return JSON formatted String.
     *  @return    String   JSON formatted String.
     */
    @Override
	public String toString(){
        return new org.json.JSONObject(objectMap).toString();
    }
    //--------------------------------------------------------------------------
}
