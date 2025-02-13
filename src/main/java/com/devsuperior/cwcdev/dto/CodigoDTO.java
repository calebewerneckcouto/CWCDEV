package com.devsuperior.cwcdev.dto;

import java.util.Objects;

import com.devsuperior.cwcdev.entities.Codigo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CodigoDTO {
    
    private Long id;
    
    @NotBlank(message = "A linguagem não pode ser vazia")
    @Size(min = 2, max = 50, message = "A linguagem deve ter entre 2 e 50 caracteres")
    private String linguagem;
    
    @NotBlank(message = "A descrição não pode ser vazia")
    @Size(min = 2, max = 200, message = "A descrição deve ter entre 2 e 200 caracteres")
    private String descricao;
    
    @NotBlank(message = "O código não pode ser vazio")
    private String codigo;
    
    private String imgUrl; // Image field in byte[] format

    public CodigoDTO() { }

    public CodigoDTO(Long id, String linguagem, String descricao, String codigo, String imagem) {
        this.id = id;
        this.linguagem = linguagem;
        this.descricao = descricao;
        this.codigo = codigo;
        this.imgUrl = imgUrl;
    }

    public CodigoDTO(Codigo entity) {
        id = entity.getId();
        linguagem = entity.getLinguagem();
        descricao = entity.getDescricao();
        codigo = entity.getCodigo();
        imgUrl = entity.getImgUrl(); // Assuming the entity has an 'imagem' field
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

   

    public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	@Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CodigoDTO other = (CodigoDTO) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "CodigoDTO [id=" + id + ", linguagem=" + linguagem + ", descricao=" + descricao + ", codigo=" + codigo + "]";
    }
}
