package DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TaskComponent {
	private long id;
	private LocalDateTime createdDt;
	private LocalDateTime deletionDt;
	private LocalDateTime lastModified;
}
