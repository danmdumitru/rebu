package rebu.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UradMonitor.
 */
@Entity
@Table(name = "urad_monitor")
public class UradMonitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_id")
    private String unitId;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "altitude")
    private String altitude;

    @Column(name = "speed")
    private String speed;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "versionsw")
    private Long versionsw;

    @Column(name = "versionhw")
    private Long versionhw;

    @Column(name = "status")
    private String status;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "detector")
    private String detector;

    @Column(name = "factor", precision=10, scale=2)
    private BigDecimal factor;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "cpm")
    private String cpm;

    @Column(name = "duty")
    private String duty;

    @Column(name = "jhi_time")
    private ZonedDateTime time;

    @Column(name = "voltage")
    private Double voltage;

    @Column(name = "pressure", precision=10, scale=2)
    private BigDecimal pressure;

    @Column(name = "humidity", precision=10, scale=2)
    private BigDecimal humidity;

    @Column(name = "luminosity", precision=10, scale=2)
    private BigDecimal luminosity;

    @Column(name = "co_2", precision=10, scale=2)
    private BigDecimal co2;

    @Column(name = "ch_2_o", precision=10, scale=2)
    private BigDecimal ch2o;

    @Column(name = "pm_25", precision=10, scale=2)
    private BigDecimal pm25;
    
    @Column(name = "voc", precision=10, scale=2)
    private BigDecimal voc;

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

    public UradMonitor latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public UradMonitor longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public UradMonitor altitude(String altitude) {
        this.altitude = altitude;
        return this;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSpeed() {
        return speed;
    }

    public UradMonitor speed(String speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCity() {
        return city;
    }

    public UradMonitor city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public UradMonitor country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getVersionsw() {
        return versionsw;
    }

    public UradMonitor versionsw(Long versionsw) {
        this.versionsw = versionsw;
        return this;
    }

    public void setVersionsw(Long versionsw) {
        this.versionsw = versionsw;
    }

    public Long getVersionhw() {
        return versionhw;
    }

    public UradMonitor versionhw(Long versionhw) {
        this.versionhw = versionhw;
        return this;
    }

    public void setVersionhw(Long versionhw) {
        this.versionhw = versionhw;
    }

    public String getStatus() {
        return status;
    }

    public UradMonitor status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public UradMonitor mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDetector() {
        return detector;
    }

    public UradMonitor detector(String detector) {
        this.detector = detector;
        return this;
    }

    public void setDetector(String detector) {
        this.detector = detector;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public UradMonitor factor(BigDecimal factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public String getTemperature() {
        return temperature;
    }

    public UradMonitor temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getCpm() {
        return cpm;
    }

    public UradMonitor cpm(String cmp) {
        this.cpm = cmp;
        return this;
    }

    public void setCpm(String cmp) {
        this.cpm = cmp;
    }

    public String getDuty() {
        return duty;
    }

    public UradMonitor duty(String duty) {
        this.duty = duty;
        return this;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public UradMonitor time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Double getVoltage() {
        return voltage;
    }

    public UradMonitor voltage(Double voltage) {
        this.voltage = voltage;
        return this;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public BigDecimal getPressure() {
        return pressure;
    }

    public UradMonitor pressure(BigDecimal pressure) {
        this.pressure = pressure;
        return this;
    }

    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getHumidity() {
        return humidity;
    }

    public UradMonitor humidity(BigDecimal humidity) {
        this.humidity = humidity;
        return this;
    }

    public void setHumidity(BigDecimal humidity) {
        this.humidity = humidity;
    }

    public BigDecimal getLuminosity() {
        return luminosity;
    }

    public UradMonitor luminosity(BigDecimal luminosity) {
        this.luminosity = luminosity;
        return this;
    }

    public void setLuminosity(BigDecimal luminosity) {
        this.luminosity = luminosity;
    }

    public BigDecimal getCo2() {
        return co2;
    }

    public UradMonitor co2(BigDecimal co2) {
        this.co2 = co2;
        return this;
    }

    public void setCo2(BigDecimal co2) {
        this.co2 = co2;
    }

    public BigDecimal getCh2o() {
        return ch2o;
    }

    public UradMonitor ch2o(BigDecimal ch2o) {
        this.ch2o = ch2o;
        return this;
    }

    public void setCh2o(BigDecimal ch2o) {
        this.ch2o = ch2o;
    }

    public BigDecimal getPm25() {
        return pm25;
    }

    public UradMonitor pm25(BigDecimal pm25) {
        this.pm25 = pm25;
        return this;
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
        UradMonitor uradMonitor = (UradMonitor) o;
        if (uradMonitor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uradMonitor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UradMonitor{" +
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
            ", pm25='" + getPm25() + "'" +
            ", voc='" + getVoc() + "'" +
            "}";
    }
}
