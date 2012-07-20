package org.kuali.mobility.computerlabs.helper;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("rooms")
public class Room implements Serializable
{
	private static final long serialVersionUID = -1201523527319118514L;
	   @XStreamAlias("name")
	   private String name;
	   @XStreamAlias("windows_inuse")
	   private int windows_inuse;
	   @XStreamAlias("linux_inuse")
	   private int linux_inuse;
	   @XStreamAlias("mac_inuse")
	   private int mac_inuse;
	   @XStreamAlias("windows_free")
	   private int windows_free;
	   @XStreamAlias("linux_free")
	   private int linux_free;
	   @XStreamAlias("mac_free")
	   private int mac_free;
	   @XStreamAlias("computers_inuse")
	   private int computers_inuse;
	   @XStreamAlias("computers_free")
	   private int computers_free;
	   
	   public Room()
	   {
		   
	   }
	
	   public String getName() {
			return name;
		}

		public void setName(String roomname) {
			this.name = roomname;
		}
		
	   /**
	 * @return the windows_inuse
	 */
	public int getWindows_inuse() {
		return windows_inuse;
	}
	/**
	 * @param windows_inuse the windows_inuse to set
	 */
	public void setWindows_inuse(int windows_inuse) {
		this.windows_inuse = windows_inuse;
	}
	/**
	 * @return the linux_inuse
	 */
	public int getLinux_inuse() {
		return linux_inuse;
	}
	/**
	 * @param linux_inuse the linux_inuse to set
	 */
	public void setLinux_inuse(int linux_inuse) {
		this.linux_inuse = linux_inuse;
	}
	/**
	 * @return the mac_inuse
	 */
	public int getMac_inuse() {
		return mac_inuse;
	}
	/**
	 * @param mac_inuse the mac_inuse to set
	 */
	public void setMac_inuse(int mac_inuse) {
		this.mac_inuse = mac_inuse;
	}
	/**
	 * @return the windows_free
	 */
	public int getWindows_free() {
		return windows_free;
	}
	/**
	 * @param windows_free the windows_free to set
	 */
	public void setWindows_free(int windows_free) {
		this.windows_free = windows_free;
	}
	/**
	 * @return the linux_free
	 */
	public int getLinux_free() {
		return linux_free;
	}
	/**
	 * @param linux_free the linux_free to set
	 */
	public void setLinux_free(int linux_free) {
		this.linux_free = linux_free;
	}
	/**
	 * @return the mac_free
	 */
	public int getMac_free() {
		return mac_free;
	}
	/**
	 * @param mac_free the mac_free to set
	 */
	public void setMac_free(int mac_free) {
		this.mac_free = mac_free;
	}
	/**
	 * @return the computers_inuse
	 */
	public int getComputers_inuse() {
		return computers_inuse;
	}
	/**
	 * @param computers_inuse the computers_inuse to set
	 */
	public void setComputers_inuse(int computers_inuse) {
		this.computers_inuse = computers_inuse;
	}
	/**
	 * @return the computers_free
	 */
	public int getComputers_free() {
		return computers_free;
	}
	/**
	 * @param computers_free the computers_free to set
	 */
	public void setComputers_free(int computers_free) {
		this.computers_free = computers_free;
	}

	
		
			
	 }  

