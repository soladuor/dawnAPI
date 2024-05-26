package com.soladuor.mapper;


import com.soladuor.pojo.Identifier;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentifierMapper {

    List<Identifier> getIdentifierList();

    Identifier getIdentifierByKey(String key);

    void updateValueByKey(@Param("key") String key, @Param("value") String value);
}
