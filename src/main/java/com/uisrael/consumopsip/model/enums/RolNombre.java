package com.uisrael.consumopsip.model.enums;

public enum RolNombre {

	ADMIN("Admin"),
	SUPERVISOR("Supervisor"),
	EMPLEADO("Empleado"),
	DIGITAL_ADMIN("Digital Admin");

	private final String etiqueta;

	RolNombre(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}
}
