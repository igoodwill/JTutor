package org.igoodwill.jtutorsb.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "forum_question")
public class ForumQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@Size(min = 6, max = 32)
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "creator_id")
	private Integer creatorId;

	@Column(name = "created")
	private Date created;

	@NotNull
	@Size(min = 16, max = 8000)
	@Column(name = "value", nullable = false)
	private String value;

	@Column(name = "closed")
	private boolean closed;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "forum_question_id")
	private List<ForumAnswer> forumAnswers = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public List<ForumAnswer> getForumAnswers() {
		return forumAnswers;
	}

	public void setForumAnswers(List<ForumAnswer> forumAnswers) {
		this.forumAnswers = forumAnswers;
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
		result = prime * result + (closed ? 1231 : 1237);
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((creatorId == null) ? 0 : creatorId.hashCode());
		result = prime * result + ((forumAnswers == null) ? 0 : forumAnswers.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ForumQuestion other = (ForumQuestion) obj;
		if (closed != other.closed)
			return false;
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
		if (forumAnswers == null) {
			if (other.forumAnswers != null)
				return false;
		} else if (!forumAnswers.equals(other.forumAnswers))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
}
