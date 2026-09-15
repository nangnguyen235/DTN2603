package entity;

import entity.enums.TypeName;

public class TypeQuestion {

    private int typeId;
    private TypeName typeName;

    public TypeQuestion(int typeId, TypeName typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public TypeName getTypeName() {
        return typeName;
    }
}
