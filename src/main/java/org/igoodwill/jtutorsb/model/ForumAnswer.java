package org.igoodwill.jtutorsb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "forum_answer")
public class ForumAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 16, max = 8000)
	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "creator_id")
	private Integer creatorId;

	@Column(name = "created")
	private Date created;

	@ElementCollection
	@CollectionTable(name = "forum_answer_liked")
	private List<Integer> liked = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public List<Integer> getLiked() {
		return liked;
	}

	public void setLiked(List<Integer> liked) {
		this.liked = liked;
	}

	public String getTime(String lang) {
		long created = this.created.getTime();
		long current = new Date().getTime();

		long sub = (current - created) / 1000;

		if (lang == "ua") {
			if (sub == 0) {
				return "щойно";
			} else if (sub < 60) {
				if (Long.toString(sub).endsWith("11"))
					return sub + " секунд тому";

				if (Long.toString(sub).endsWith("12"))
					return sub + " секунд тому";

				if (Long.toString(sub).endsWith("13"))
					return sub + " секунд тому";

				if (Long.toString(sub).endsWith("14"))
					return sub + " секунд тому";

				if (Long.toString(sub).endsWith("1"))
					return sub + " секунду тому";

				if (Long.toString(sub).endsWith("2"))
					return sub + " секунди тому";

				if (Long.toString(sub).endsWith("3"))
					return sub + " секунди тому";

				if (Long.toString(sub).endsWith("4"))
					return sub + " секунди тому";

				return sub + " секунд тому";
			} else if ((sub /= 60) < 60) {
				if (Long.toString(sub).endsWith("11"))
					return sub + " хвилин тому";

				if (Long.toString(sub).endsWith("12"))
					return sub + " хвилин тому";

				if (Long.toString(sub).endsWith("13"))
					return sub + " хвилин тому";

				if (Long.toString(sub).endsWith("14"))
					return sub + " хвилин тому";

				if (Long.toString(sub).endsWith("1"))
					return sub + " хвилину тому";

				if (Long.toString(sub).endsWith("2"))
					return sub + " хвилини тому";

				if (Long.toString(sub).endsWith("3"))
					return sub + " хвилини тому";

				if (Long.toString(sub).endsWith("4"))
					return sub + " хвилини тому";

				return sub + " хвилин тому";
			} else if ((sub /= 60) < 24) {
				if (Long.toString(sub).endsWith("11"))
					return sub + " годин тому";

				if (Long.toString(sub).endsWith("12"))
					return sub + " годин тому";

				if (Long.toString(sub).endsWith("13"))
					return sub + " годин тому";

				if (Long.toString(sub).endsWith("14"))
					return sub + " годин тому";

				if (Long.toString(sub).endsWith("1"))
					return sub + " годину тому";

				if (Long.toString(sub).endsWith("2"))
					return sub + " години тому";

				if (Long.toString(sub).endsWith("3"))
					return sub + " години тому";

				if (Long.toString(sub).endsWith("4"))
					return sub + " години тому";

				return sub + " годин тому";
			} else if ((sub /= 24) < 30) {
				if (Long.toString(sub).endsWith("11"))
					return sub + " днів тому";

				if (Long.toString(sub).endsWith("12"))
					return sub + " днів тому";

				if (Long.toString(sub).endsWith("13"))
					return sub + " днів тому";

				if (Long.toString(sub).endsWith("14"))
					return sub + " днів тому";

				if (Long.toString(sub).endsWith("1"))
					return sub + " день тому";

				if (Long.toString(sub).endsWith("2"))
					return sub + " дні тому";

				if (Long.toString(sub).endsWith("3"))
					return sub + " дні тому";

				if (Long.toString(sub).endsWith("4"))
					return sub + " дні тому";

				return sub + " днів тому";
			} else if ((sub /= 30) < 12) {
				if (Long.toString(sub).endsWith("11"))
					return sub + " місяців тому";

				if (Long.toString(sub).endsWith("12"))
					return sub + " місяців тому";

				if (Long.toString(sub).endsWith("13"))
					return sub + " місяців тому";

				if (Long.toString(sub).endsWith("14"))
					return sub + " місяців тому";

				if (Long.toString(sub).endsWith("1"))
					return sub + " місяць тому";

				if (Long.toString(sub).endsWith("2"))
					return sub + " місяці тому";

				if (Long.toString(sub).endsWith("3"))
					return sub + " місяці тому";

				if (Long.toString(sub).endsWith("4"))
					return sub + " місяці тому";

				return sub + " місяців тому";
			} else {
				sub /= 12;

				if (Long.toString(sub).endsWith("11"))
					return sub + " років тому";

				if (Long.toString(sub).endsWith("12"))
					return sub + " років тому";

				if (Long.toString(sub).endsWith("13"))
					return sub + " років тому";

				if (Long.toString(sub).endsWith("14"))
					return sub + " років тому";

				if (Long.toString(sub).endsWith("1"))
					return sub + " рік тому";

				if (Long.toString(sub).endsWith("2"))
					return sub + " роки тому";

				if (Long.toString(sub).endsWith("3"))
					return sub + " роки тому";

				if (Long.toString(sub).endsWith("4"))
					return sub + " роки тому";

				return sub + " років тому";
			}
		} else {
			if (sub == 0) {
				return "now";
			} else if (sub < 60) {
				if (sub == 1)
					return sub + " second ago";

				return sub + " seconds ago";
			} else if ((sub /= 60) < 60) {
				if (sub == 1)
					return sub + " minute ago";

				return sub + " minutes ago";
			} else if ((sub /= 60) < 24) {
				if (sub == 1)
					return sub + " hour ago";

				return sub + " hours ago";
			} else if ((sub /= 24) < 30) {
				if (sub == 1)
					return sub + " day ago";

				return sub + " days ago";
			} else if ((sub /= 30) < 12) {
				if (sub == 1)
					return sub + " month ago";

				return sub + " months ago";
			} else {
				if (sub == 1)
					return sub + " year ago";

				return (sub / 12) + " years ago";
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((creatorId == null) ? 0 : creatorId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((liked == null) ? 0 : liked.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForumAnswer other = (ForumAnswer) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (creatorId == null) {
			if (other.creatorId != null)
				return false;
		} else if (!creatorId.equals(other.creatorId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (liked == null) {
			if (other.liked != null)
				return false;
		} else if (!liked.equals(other.liked))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
