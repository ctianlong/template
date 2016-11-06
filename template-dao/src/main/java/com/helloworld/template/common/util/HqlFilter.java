package com.helloworld.template.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class HqlFilter {
	
	private HttpServletRequest request;
	  private Map<String, Object> params = new HashMap<>();
	  private StringBuffer hql = new StringBuffer();
	  private String sort;
	  private String order = "asc";
	  
	  public HqlFilter() {}
	  
	  public HqlFilter(HttpServletRequest request)
	  {
	    this.request = request;
	    addFilter(request);
	  }
	  
	  public void addSort(String sort)
	  {
	    this.sort = sort;
	  }
	  
	  public void addOrder(String order)
	  {
	    this.order = order;
	  }
	  
	  private String getSqlOperator(String operator)
	  {
	    if (StringUtils.equalsIgnoreCase(operator, "EQ")) {
	      return " = ";
	    }
	    if (StringUtils.equalsIgnoreCase(operator, "NE")) {
	      return " != ";
	    }
	    if (StringUtils.equalsIgnoreCase(operator, "LT")) {
	      return " < ";
	    }
	    if (StringUtils.equalsIgnoreCase(operator, "GT")) {
	      return " > ";
	    }
	    if (StringUtils.equalsIgnoreCase(operator, "LE")) {
	      return " <= ";
	    }
	    if (StringUtils.equalsIgnoreCase(operator, "GE")) {
	      return " >= ";
	    }
	    if ((StringUtils.equalsIgnoreCase(operator, "LK")) || (StringUtils.equalsIgnoreCase(operator, "RLK")) || (StringUtils.equalsIgnoreCase(operator, "LLK"))) {
	      return " like ";
	    }
	    return "";
	  }
	  
	  public String getWhereHql()
	  {
	    return this.hql.toString();
	  }
	  
	  public String getWhereAndOrderHql()
	  {
	    if ((!StringUtils.isBlank(this.sort)) && (!StringUtils.isBlank(this.order)))
	    {
	      if (this.sort.indexOf(".") < 1) {
	        this.sort = ("t." + this.sort);
	      }
	      this.hql.append(" order by " + this.sort + " " + this.order + " ");
	    }
	    else if (this.request != null)
	    {
	      String s = this.request.getParameter("sort");
	      String o = this.request.getParameter("order");
	      if (!StringUtils.isBlank(s)) {
	        this.sort = s;
	      }
	      if (!StringUtils.isBlank(o)) {
	        this.order = o;
	      }
	      if ((!StringUtils.isBlank(this.sort)) && (!StringUtils.isBlank(this.order)))
	      {
	        if (this.sort.indexOf(".") < 1) {
	          this.sort = ("t." + this.sort);
	        }
	        this.hql.append(" order by " + this.sort + " " + this.order + " ");
	      }
	    }
	    return this.hql.toString();
	  }
	  
	  public Map<String, Object> getParams()
	  {
	    return this.params;
	  }
	  
	  public void addFilter(HttpServletRequest request)
	  {
	    Enumeration<String> names = request.getParameterNames();
	    while (names.hasMoreElements())
	    {
	      String name = (String)names.nextElement();
	      String value = request.getParameter(name);
	      addFilter(name, value);
	    }
	  }
	  
	  public void addFilter(String name, String value)
	  {
	    if ((name != null) && (value != null) && 
	      (name.startsWith("QUERY_")))
	    {
	      String[] filterParams = StringUtils.split(name, "_");
	      if (filterParams.length == 4)
	      {
	        String columnName = filterParams[1].replaceAll("#", ".");
	        String columnType = filterParams[2];
	        String operator = filterParams[3];
	        String placeholder = UUID.randomUUID().toString().replace("-", "");
	        if (this.hql.toString().indexOf(" where 1=1") < 0) {
	          this.hql.append("  where 1=1 ");
	        }
	        this.hql.append(" and " + columnName + " " + getSqlOperator(operator) + " :param" + placeholder + " ");
	        this.params.put("param" + placeholder, getObjValue(columnType, operator, value));
	      }
	    }
	  }
	  
	  private Object getObjValue(String columnType, String operator, String value)
	  {
	    if (StringUtils.equalsIgnoreCase(columnType, "S"))
	    {
	      if (StringUtils.equalsIgnoreCase(operator, "LK")) {
	        value = "%%" + value + "%%";
	      } else if (StringUtils.equalsIgnoreCase(operator, "RLK")) {
	        value = value + "%%";
	      } else if (StringUtils.equalsIgnoreCase(operator, "LLK")) {
	        value = "%%" + value;
	      }
	      return value;
	    }
	    if (StringUtils.equalsIgnoreCase(columnType, "L")) {
	      return Long.valueOf(Long.parseLong(value));
	    }
	    if (StringUtils.equalsIgnoreCase(columnType, "I")) {
	      return Integer.valueOf(Integer.parseInt(value));
	    }
	    if (StringUtils.equalsIgnoreCase(columnType, "D")) {
	      try
	      {
	        return DateUtils.parseDate(value, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/MM/dd" });
	      }
	      catch (ParseException e)
	      {
	        e.printStackTrace();
	      }
	    }
	    if (StringUtils.equalsIgnoreCase(columnType, "ST")) {
	      return Short.valueOf(Short.parseShort(value));
	    }
	    if (StringUtils.equalsIgnoreCase(columnType, "BD")) {
	      return BigDecimal.valueOf(Long.parseLong(value));
	    }
	    if (StringUtils.equalsIgnoreCase(columnType, "FT")) {
	      return Float.valueOf(Float.parseFloat(value));
	    }
	    return null;
	  }

}
