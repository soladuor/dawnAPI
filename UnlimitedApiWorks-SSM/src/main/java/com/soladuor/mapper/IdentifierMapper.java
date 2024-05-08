package com.soladuor.mapper;


import com.soladuor.pojo.Identifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdentifierMapper {

    List<Identifier> getIdentifierList();

    Identifier getIdentifierByKey(String key);
}
