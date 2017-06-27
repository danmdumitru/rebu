package rebu.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

import rebu.domain.UradMonitor;

/**
 * A DTO for the UradMonitor entity.
 */
public class UradMonitorDTO implements Serializable {
	

    public UradMonitorDTO() {
	}

	public UradMonitorDTO(double[] forecast ) {
		super();
		this.forecast = forecast;
	}

	public UradMonitorDTO(Long id, String unit_id, String detector) {
		super();
		this.id = id;
		this.unitId = unit_id;
		this.detector = detector;
	}
	
	 public UradMonitorDTO(UradMonitor u) {
		 if (u != null) {
			this.id = u.getId();
			this.unitId = u.getUnitId();
		}
	}

	private Long id;

    private String unitId;

    private String latitude;

    private String longitude;

    private String altitude;

    private String speed;

    private String city;

    private String country;

    private Long versionsw;

    private Long versionhw;

    private String status;

    private String mobile;

    private String detector;

    private BigDecimal factor;

    private String temperature;

    private String cpm;

    private String duty;

    private ZonedDateTime time;

    private Double voltage;

    private BigDecimal pressure;

    private BigDecimal humidity;

    private BigDecimal luminosity;

    private BigDecimal co2;

    private BigDecimal ch2o;

    private BigDecimal pm25;
    
    private BigDecimal voc;
    
    private double[] forecast; 
    
    

    public void setForecast(double[] forecast) {
		this.forecast = forecast;
	}

	public double[] getForecast() {
		return forecast;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unit_id) {
        this.unitId = unit_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getVersionsw() {
        return versionsw;
    }

    public void setVersionsw(Long versionsw) {
        this.versionsw = versionsw;
    }

    public Long getVersionhw() {
        return versionhw;
    }

    public void setVersionhw(Long versionhw) {
        this.versionhw = versionhw;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDetector() {
        return detector;
    }

    public void setDetector(String detector) {
        this.detector = detector;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCpm() {
        return cpm;
    }

    public void setCpm(String cmp) {
        this.cpm = cmp;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public BigDecimal getPressure() {
        return pressure;
    }

    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public BigDecimal getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(BigDecimal luminosity) {
        this.luminosity = luminosity;
    }

    public BigDecimal getCo2() {
        return co2;
    }

    public void setCo2(BigDecimal co2) {
        this.co2 = co2;
    }

    public BigDecimal getCh2o() {
        return ch2o;
    }

    public void setCh2o(BigDecimal ch2o) {
        this.ch2o = ch2o;
    }

    public BigDecimal getPm25() {
        return pm25;
    }

    public void setPm25(BigDecimal pm25) {
        this.pm25 = pm25;
    }

    public BigDecimal getVoc() {
		return voc;
	}

	public void setVoc(BigDecimal voc) {
		this.voc = voc;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UradMonitorDTO uradMonitorDTO = (UradMonitorDTO) o;
        if(uradMonitorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uradMonitorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UradMonitorDTO{" +
            "id=" + getId() +
            ", unit_id='" + getUnitId() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", altitude='" + getAltitude() + "'" +
            ", speed='" + getSpeed() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", versionsw='" + getVersionsw() + "'" +
            ", versionhw='" + getVersionhw() + "'" +
            ", status='" + getStatus() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", detector='" + getDetector() + "'" +
            ", factor='" + getFactor() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", cpm='" + getCpm() + "'" +
            ", duty='" + getDuty() + "'" +
            ", time='" + getTime() + "'" +
            ", voltage='" + getVoltage() + "'" +
            ", pressure='" + getPressure() + "'" +
            ", humidity='" + getHumidity() + "'" +
            ", luminosity='" + getLuminosity() + "'" +
            ", co2='" + getCo2() + "'" +
            ", ch2o='" + getCh2o() + "'" +
            ", pm_25='" + getPm25() + "'" +
            "}";
    }
}
