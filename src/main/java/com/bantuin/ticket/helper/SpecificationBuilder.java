package com.bantuin.ticket.helper;

import com.bantuin.ticket.util.LogUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SpecificationBuilder<T> {

    public static final String TAG = "SpesificationBuilder";

    private final List<SearchCriteria> params;

    public SpecificationBuilder() {
        params = new ArrayList<>();
    }

    public void generateSearch(String search){


        String[] searchs = search.split(",");
        for(String clause : searchs){
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(.+)");
            Matcher matcher = pattern.matcher(clause );
            matcher.find();
            with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
    }

    public SpecificationBuilder add(SearchCriteria searchCriteria) {
        this.params.add(searchCriteria);
        return this;
    }

    public SpecificationBuilder addAll(SearchCriteria[] search) {
        if (search != null && search.length > 0) {
            params.addAll(Arrays.asList(search));
        }
        return this;
    }

    public SpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));

        return this;
    }

    public Specification<T> build(String search) {
        if(search==null || search.isEmpty())
            return null;
        generateSearch(search);
        if (params.size() == 0) {
            return null;
        }

        List<Specification<T>> specs = params.stream()
                .map(ModelSpesification<T>::new)
                .collect(Collectors.toList());

        Specification<T> result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

    @Deprecated
    public Specification<T> build(SearchCriteria[] search) {
        List<SearchCriteria> newparams = new ArrayList<>();
        if (!params.isEmpty()) {
            newparams.addAll(params);
        }

        if (search != null && search.length > 0) {
            newparams.addAll(Arrays.asList(search));
        }
        LogUtil.debug(TAG, "jumlah param : " + newparams.size());

        List<Specification<T>> specs = newparams.stream()
                .map(ModelSpesification<T>::new)
                .collect(Collectors.toList());

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }

    public Specification<T> build() {
        List<SearchCriteria> newparams = new ArrayList<>();
        if (!params.isEmpty()) {
            newparams.addAll(params);
        }
        if (newparams.isEmpty()) {
            return null;
        }
        LogUtil.debug(TAG, "jumlah param : " + newparams.size());

        List<Specification<T>> specs = newparams.stream()
                .map(ModelSpesification<T>::new)
                .collect(Collectors.toList());

        Specification<T> result = specs.get(0);

        for (int i = 1; i < specs.size(); i++) {
            result = Specification.where(result)
                    .and(specs.get(i));
        }
        return result;
    }
}
