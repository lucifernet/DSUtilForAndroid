package ischool.dsa.client.target;

import java.io.Serializable;

public interface ITargetURLProvider extends Serializable {
	public String getOriginal();

	public String getTargetURL();
}
