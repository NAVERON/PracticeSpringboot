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
@Table(name = "ship_track_point") 
public class ShipTrackPoint implements Cloneable { 

	private static final Logger log = LoggerFactory.getLogger(ShipTrackPoint.class);
    
	// 不需要column注解的情况是 使用大小驼峰命名 如 a_b -> 对象 aB 属性 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private Long id; // 自增主键  
	@Column(name = "ship_id")
	@NonNull
	private Long shipId; // 船舶id @See Ship  必须赋值 
	@Column(name = "rotation_acceleration")
	private Float rotationAcceleration; // 转向角加速度 
	@Column(name = "sog_speed")
	private Float sogSpeed; // 对地船舶速度 
	@Column(name = "cog_cource")
	private Float cogCource; // 对地航向 
	@Column(name = "speed")
	private Float speed; // 船速 speed - 流速 = sogSpeed 
	@Column(name = "cource")
	private Float cource; // 船向 cource = cogCource 
	@Column(name = "rudder")
	private Float rudder; // 船舶舵角 
	@Column(name = "lng")
	private Float longitude; // 经度 
	@Column(name = "lat")
	private Float latitude; // 纬度 
	@Column(name = "create_time", insertable = false, updatable = false)
	private LocalDateTime createTime; // 轨迹点创建时间 
	
	@Deprecated
	public ShipTrackPoint() {
		this.shipId = 0L;
	}
	public ShipTrackPoint(Long shipId) { 
		this.shipId = shipId;
	}
	public ShipTrackPoint(Builder builder) {
		this.shipId = builder.shipId;
		this.rotationAcceleration = builder.rotationAcceleration;
		this.sogSpeed = builder.sogSpeed;
		this.cogCource = builder.cogCource;
		this.speed = builder.speed;
		this.cource = builder.cource;
		this.rudder = builder.rudder;
		this.longitude = builder.longitude;
		this.latitude = builder.latitude;
	}
	
	public static Builder createBuilder() {
		return new Builder();
	}
	
	public static class Builder { 
		// 以后可以实现快速创建和参数验证 
		//private Long id; // 自增主键  
		private Long shipId; // 船舶id @See Ship  必须赋值 
		private Float rotationAcceleration = 0F; // 转向角加速度 
		private Float sogSpeed = 0F; // 对地船舶速度 
		private Float cogCource = 0F; // 对地航向 
		private Float speed = 0F; // 船速 speed - 流速 = sogSpeed 
		private Float cource = 0F; // 船向 cource = cogCource 
		private Float rudder = 0F; // 船舶舵角 
		private Float longitude = 0F; // 经度 
		private Float latitude = 0F; // 纬度 
		//private LocalDateTime createTime; // 轨迹点创建时间 
		
		public Builder() {}
		public Builder shipId(Long shipId) {
			this.shipId = shipId;
			return this;
		}
		public Builder rotationAcceleration(Float rotationAcceleration) {
			this.rotationAcceleration = rotationAcceleration;
			return this;
		}
		public Builder sogSpeed(Float sogSpeed) {
			this.sogSpeed = sogSpeed;
			return this;
		}
		public Builder cogCource(Float cogCource) {
			this.cogCource = cogCource;
			return this;
		}
		public Builder speed(Float speed) {
			this.speed = speed;
			return this;
		}
		public Builder cource(Float cource) {
			this.cource = cource;
			return this;
		}
		public Builder rudder(Float rudder) {
			this.rudder = rudder;
			return this;
		}
		public Builder longitude(Float longitude) {
			this.longitude = longitude;
			return this;
		}
		public Builder latitude(Float latitude) {
			this.latitude = latitude;
			return this;
		}
		
		public ShipTrackPoint build() {
			if(this.shipId == null) {
				throw new IllegalArgumentException("shipId of ShipTrackPoint must required !");
			}
			return new ShipTrackPoint(this);
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	// id 不覆盖 
	public void overrideAttributes(ShipTrackPoint another) {
		if (another == null) {
			log.error("another User is null !!!");
			return;
		}
		
		this.shipId = another.shipId;
		this.rotationAcceleration = another.rotationAcceleration;
		this.sogSpeed = another.sogSpeed;
		this.cogCource = another.cogCource;
		this.speed = another.speed;
		this.cource = another.cource;
		this.rudder = another.rudder;
		this.longitude = another.longitude;
		this.latitude = another.latitude;
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

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public Float getRotationAcceleration() {
		return rotationAcceleration;
	}

	public void setRotationAcceleration(Float rotationAcceleration) {
		this.rotationAcceleration = rotationAcceleration;
	}

	public Float getSogSpeed() {
		return sogSpeed;
	}

	public void setSogSpeed(Float sogSpeed) {
		this.sogSpeed = sogSpeed;
	}

	public Float getCogCource() {
		return cogCource;
	}

	public void setCogCource(Float cogCource) {
		this.cogCource = cogCource;
	}

	public Float getSpeed() {
		return speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

	public Float getCource() {
		return cource;
	}

	public void setCource(Float cource) {
		this.cource = cource;
	}

	public Float getRudder() {
		return rudder;
	}

	public void setRudder(Float rudder) {
		this.rudder = rudder;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ShipTrackPoint [id=" + id + ", shipId=" + shipId + ", rotationAcceleration=" + rotationAcceleration
				+ ", sogSpeed=" + sogSpeed + ", cogCource=" + cogCource + ", speed=" + speed + ", cource=" + cource
				+ ", rudder=" + rudder + ", longitude=" + longitude + ", latitude=" + latitude + ", createTime="
				+ createTime + "]";
	}
	
	
}









