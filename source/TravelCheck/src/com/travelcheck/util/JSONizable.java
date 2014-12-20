package com.travelcheck.util;

import java.util.Map;

/*
 *  Every class which can be sent as Model data to the JSP should implement this
 *  Interface.
 */
public interface JSONizable {
    /*
     *  Method to return the equivalent JSON Object for the class. Should be
     *  implemented for the required fields that need to be sent as model data.
     *  
     *  @return JSONObject  The equivalent JSON Object for the class.
     */
    public Map<String, Object> toJSON();
    //--------------------------------------------------------------------------
}
