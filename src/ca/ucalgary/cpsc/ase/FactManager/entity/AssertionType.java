package ca.ucalgary.cpsc.ase.FactManager.entity;

import java.io.Serializable;

public enum AssertionType implements Serializable {
	
	ASSERT_EQUALS("assertEquals"),
	ASSERT_ARRAY_EQUALS("assertArrayEquals"),
	ASSERT_TRUE("assertTrue"),
	ASSERT_FALSE("assertFalse"),
	ASSERT_NULL("assertNull"),
	ASSERT_NOT_NULL("assertNotNull"),
	ASSERT_SAME("assertSame"),
	ASSERT_NOT_SAME("assertNotSame"),
	ASSERT_THAT("assertThat"),
	FAIL("fail");

	private String name;
	
	AssertionType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static AssertionType getType(String name) {
		AssertionType[] types = values();
		for (AssertionType type : types)
			if (type.getName().equals(name))
				return type;
		return null;
	}
}
