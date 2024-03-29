package com.eron.practice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;


@Entity
@Table(name = "ship") 
public class Ship implements Cloneable { 

	private static final Logger log = LoggerFactory.getLogger(Ship.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id; // 自动生成的id 
	@Column(name = "user_id")
	@NonNull
	private Long userId; // 用户ID @See User  **必须赋值** 
	@Column(name = "name")
	private String name; // 船舶名称   **必须赋值** 
	@Column(name = "mmsi")
	private String mmsi; // mmsi Maritime Mobile Service Identify 水上移动通信业务标识码
	@Column(name = "imo_number")
	private String imoNumber; // IMO ship identification number  
	@Column(name = "call_number")
	private String callNumber; // CALL SIGN,是国际海事组织IMO指定给每条船舶唯一的识别信号，CALL SIGN主要的作用就是在船舶海上联络、码头靠泊、信息报告的时候使用 
	@Column(name = "type")
	private Integer type; // 船舶类型 
	@Column(name = "electronic_type")
	private Integer electronicType; // 船舶电子设备类型 GPS AIS 等电子设备, router项目有编码 
	@Column(name = "draft")
	private Float draft; // 船舶吃水 m/dt 
	@Column(name = "create_time", insertable = false, updatable = false)
	private LocalDateTime createTime; // 船舶创建时间 
	@Column(name = "update_time", insertable = false, updatable = false)
	private LocalDateTime updateTime; // 船舶属性修改时间 
	
	@Deprecated
	public Ship() {  // 禁用
		this.userId = 0L; 
		this.name = "NULL";
	}
	
	public Ship(Long userId, String name) { 
		this.userId = userId;
		this.name = name;
	}
	
	public Ship(Builder builder) {
		this.userId = builder.userId;
		this.name = builder.name;
		this.mmsi = builder.mmsi;
		this.imoNumber = builder.imoNumber;
		this.callNumber = builder.callNumber;
		this.type = builder.type;
		this.electronicType = builder.electronicType;
		this.draft = builder.draft;
	}
	
	/**
	 * 使用 Ship.createBuilder().build();
	 * @return Builder obj 
	 */
	public static Builder createBuilder() { 
		return new Builder();
	}
	
	public static class Builder {
		//private Long id; // 自动生成的id 
		private Long userId; // 用户ID @See User  **必须赋值** 
		private String name = "NULL"; // 船舶名称   **必须赋值** 
		private String mmsi = "NULL"; // mmsi Maritime Mobile Service Identify 水上移动通信业务标识码
		private String imoNumber = "NULL"; // IMO ship identification number  
		private String callNumber = "NULL"; // CALL SIGN,是国际海事组织IMO指定给每条船舶唯一的识别信号，CALL SIGN主要的作用就是在船舶海上联络、码头靠泊、信息报告的时候使用 
		private Integer type = 0; // 船舶类型 
		private Integer electronicType = 0; // 船舶电子设备类型 GPS AIS 等电子设备, router项目有编码 
		private Float draft = 0F; // 船舶吃水 m/dt 
		//private LocalDateTime createTime; // 船舶创建时间 
		//private LocalDateTime updateTime; // 船舶属性修改时间 
		public Builder() {}
		public Builder userId(Long userId) {
			this.userId = userId;
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		public Builder mmsi(String mmsi) {
			this.mmsi = mmsi;
			return this;
		}
		public Builder imoNumber(String imoNumber) {
			this.imoNumber = imoNumber;
			return this;
		}
		public Builder callNumber(String callNumber) {
			this.callNumber = callNumber;
			return this;
		}
		public Builder type(Integer type) {
			this.type = type;
			return this;
		}
		public Builder electronicType(Integer electronicType) {
			this.electronicType = electronicType;
			return this;
		}
		public Builder draft(Float draft) {
			this.draft = draft;
			return this;
		}
		
		public Ship build() {
			// 检查参数合法化 
			if (this.userId == null) {
				throw new IllegalArgumentException("userId of Ship is Required !");
			}
			return new Ship(this);
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	// id 和 updatetime不覆盖 
	public void overrideAttributes(Ship another) {
		if (another == null) {
			log.error("another User is null !!!");
			return;
		}
		
		this.userId = another.userId;
		this.name = another.name;
		this.mmsi = another.mmsi;
		this.imoNumber = another.imoNumber;
		this.callNumber = another.callNumber;
		this.type = another.type;
		this.electronicType = another.electronicType;
		this.draft = another.draft;
		this.createTime = another.createTime;
	}

	/**
	 * pojo 通用 getter 和 setter 
	 * @return 
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getImoNumber() {
		return imoNumber;
	}

	public void setImoNumber(String imoNumber) {
		this.imoNumber = imoNumber;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getElectronicType() {
		return electronicType;
	}

	public void setElectronicType(Integer electronicType) {
		this.electronicType = electronicType;
	}

	public Float getDraft() {
		return draft;
	}

	public void setDraft(Float draft) {
		this.draft = draft;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Ship [id=" + id + ", userId=" + userId + ", name=" + name + ", mmsi=" + mmsi + ", imoNumber="
				+ imoNumber + ", callNumber=" + callNumber + ", type=" + type + ", electronicType=" + electronicType
				+ ", draft=" + draft + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
}









