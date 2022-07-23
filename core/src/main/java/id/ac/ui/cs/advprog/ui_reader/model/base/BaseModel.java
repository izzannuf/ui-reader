package id.ac.ui.cs.advprog.ui_reader.model.base;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseModel {
  @Column(name="created_at", insertable=false, updatable=false)
  protected Date createdAt = new Date();

  @LastModifiedDate
  @Column(name = "updated_at")
  protected Date updatedAt = new Date();

  @Column(name="is_active")
  protected boolean isActive = true;
}
