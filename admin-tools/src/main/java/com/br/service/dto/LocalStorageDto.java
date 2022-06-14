package com.br.service.dto;

import lombok.Getter;
import lombok.Setter;
import com.br.base.BaseDTO;
import java.io.Serializable;

/**
*
* @date 2019-09-05
*/
@Getter
@Setter
public class LocalStorageDto extends BaseDTO implements Serializable {

    private Long id;

    private String realName;

    private String name;

    private String suffix;

    private String type;

    private String size;
}