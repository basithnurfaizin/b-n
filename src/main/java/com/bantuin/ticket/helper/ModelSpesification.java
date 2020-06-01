package com.bantuin.ticket.helper;

import com.bantuin.ticket.util.LogUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ModelSpesification<T> implements Specification<T> {

    public static final String TAG = "ModelSpesification";

    public static final String[] DATE_FORMATS = new String[]{"dd/MM/yyyy", "dd-MM-yyyy"};

    private SearchCriteria criteria;

    public ModelSpesification(SearchCriteria criteria) {
        super();
        this.criteria = criteria;
    }

    public static String[] extractModelAndPropertiesFrom(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        String[] results = key.split("\\.");
        LogUtil.debug(TAG, results);
        return results;
    }

    public static <K, T> Path<K> generatePathFrom(Root<T> root, String key) {
        String[] modelProps = extractModelAndPropertiesFrom(key);
        if (modelProps != null && modelProps.length > 0) {
            Path<K> path = null;
            for (String prop : modelProps) {
                path = (path == null) ? root.get(prop) : path.get(prop);
            }
            return path;
        }
        return root.get(key);
    }

    private Date generateDateFromString(String value) {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                return sdf.parse(value);
            } catch (ParseException e) {
                LogUtil.error(TAG, e);
            }
        }
        return null;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Object value = criteria.getValue();
        Date valDate = null;
        boolean isDate = false;
        final Path<Object> path = generatePathFrom(root, criteria.getKey());
        if (path.getJavaType() == Date.class){
            isDate = true;
            valDate = generateDateFromString(value.toString());
            LogUtil.debug(TAG, "is valDate null: " + (valDate == null));
        }

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if(isDate)
                return builder.greaterThan(root.<Date> get(criteria.getKey()), valDate);
            return builder.greaterThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if(isDate)
                return builder.lessThan(root.<Date> get(criteria.getKey()), valDate);
            return builder.lessThan(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase(">=")) {
            if(isDate)
                return builder.greaterThanOrEqualTo(root.<Date> get(criteria.getKey()), valDate);
            return builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("<=")) {
            if(isDate)
                return builder.lessThanOrEqualTo(root.<Date> get(criteria.getKey()), valDate);
            return builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }

        else if (criteria.getOperation().equalsIgnoreCase("like")) {
            String[] modelProps = extractModelAndPropertiesFrom(criteria.getKey());
            if (path.getJavaType() == String.class) {
                if (modelProps != null) {
                    final Path<String> newPath = generatePathFrom(root, criteria.getKey());
                    LogUtil.debug(TAG, "modelprops not null");
                    return builder.like(newPath, "%" + criteria.getValue() + "%");
                }
                return builder.like(root.<String> get(criteria.getKey()), "%" + criteria.getValue() + "%");
            }
        }
        else if (criteria.getOperation().equalsIgnoreCase("IN")) {
            return builder.in(generatePathFrom(root, criteria.getKey())).value(criteria.getValue());
        } else {
            if (isDate) {
                LogUtil.debug(TAG, "equal date");
                return builder.equal(root.<Date>get(criteria.getKey()), valDate);
            }
            if (criteria.getKey().contains(".")) {
                return builder.equal(generatePathFrom(root, criteria.getKey()), criteria.getValue());
            }
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }

        return null;
    }
}
