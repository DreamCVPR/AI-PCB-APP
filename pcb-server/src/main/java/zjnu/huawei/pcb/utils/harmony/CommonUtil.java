package zjnu.huawei.pcb.utils.harmony;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.config.exception.CommonJsonException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 本后台接口系统常用的json工具类
 */
public class CommonUtil {

    /**
     * 返回一个info为空对象的成功消息的json
     */
    public static JSONObject successJson() {
        return successJson(new JSONObject());
    }

    /**
     * 返回一个返回码为200的json
     */
    public static JSONObject successJson(Object info) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", Constants.SUCCESS_CODE);
        resultJson.put("message", Constants.SUCCESS_MSG);
        resultJson.put("result", info);
        return resultJson;
    }

    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(ErrorEnum errorEnum) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", errorEnum.getErrorCode());
        resultJson.put("message", errorEnum.getErrorMsg());
        resultJson.put("result", null);
        return resultJson;
    }

    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(int httpStatus) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", httpStatus);
        resultJson.put("message", "error");
        return resultJson;
    }

    /**
     * 返回错误信息JSON
     */
    public static JSONObject errorJson(int httpStatus, String msg) {
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", httpStatus);
        resultJson.put("message", "error");
        resultJson.put("result", msg);
        return resultJson;
    }


    /**
     * 将request参数值转为json
     */
    public static JSONObject request2Json(HttpServletRequest request) {
        JSONObject requestJson = new JSONObject();
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] pv = request.getParameterValues(paramName);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < pv.length; i++) {
                if (pv[i].length() > 0) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append(pv[i]);
                }
            }
            requestJson.put(paramName, sb.toString());
        }
        return requestJson;
    }

    /**
     * 将request转JSON
     * 并且验证非空字段
     */
    public static JSONObject convert2JsonAndCheckRequiredColumns(HttpServletRequest request, String requiredColumns) {
        JSONObject jsonObject = request2Json(request);
        hasAllRequired(jsonObject, requiredColumns);
        return jsonObject;
    }

    /**
     * 验证是否含有全部必填字段
     *
     * @param requiredColumns 必填的参数字段名称 逗号隔开 比如"userId,name,telephone"
     */
    public static void hasAllRequired(final JSONObject jsonObject, String requiredColumns) {
        if (!StringTools.isNullOrEmpty(requiredColumns)) {
            //验证字段非空
            String[] columns = requiredColumns.split(",");
            String missCol = "";
            for (String column : columns) {
                Object val = jsonObject.get(column.trim());
                if (StringTools.isNullOrEmpty(val)) {
                    missCol += column + "  ";
                }
            }
            if (!StringTools.isNullOrEmpty(missCol)) {
                jsonObject.clear();
                jsonObject.put("status", ErrorEnum.E_90003.getErrorCode());
                jsonObject.put("message", "缺少必填参数:" + missCol.trim());
                jsonObject.put("result", new JSONObject());
                throw new CommonJsonException(jsonObject);
            }
        }
    }

    /**
     * 在分页查询之前,为查询条件里加上分页参数
     *
     * @param paramObject    查询条件json
     * @param defaultPageRow 默认的每页条数,即前端不传pageRow参数时的每页条数
     */
    private static void fillPageParam(final JSONObject paramObject, int defaultPageRow) {
        int pageNum = paramObject.getIntValue("pageNum");
        pageNum = pageNum == 0 ? 1 : pageNum;
        int pageRow = paramObject.getIntValue("pageRow");
        pageRow = pageRow == 0 ? defaultPageRow : pageRow;
        paramObject.put("offSet", (pageNum - 1) * pageRow);
        paramObject.put("pageRow", pageRow);
        paramObject.put("pageNum", pageNum);
        //删除此参数,防止前端传了这个参数,pageHelper分页插件检测到之后,拦截导致SQL错误
        paramObject.remove("pageSize");
    }

    /**
     * 分页查询之前的处理参数
     * 没有传pageRow参数时,默认每页10条.
     */
    public static void fillPageParam(final JSONObject paramObject) {
        fillPageParam(paramObject, 10);
    }
}
