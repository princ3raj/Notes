package com.example.notes.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")

public class Notes implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Title")
    private String Title;

    @ColumnInfo(name = "Content")
    private String Content;

    @ColumnInfo(name = "TimeStamps")
    private  String TimeStamps;

    public Notes(String Title, String Content, String TimeStamps, int id)
    {
       this.Title = Title;
       this.Content = Content;
       this.TimeStamps = TimeStamps;
       this.id=id;
    }



   @Ignore
    public Notes()
    {


    }


    protected Notes(Parcel in) {
        id = in.readInt();
        Title = in.readString();
        Content = in.readString();
        TimeStamps = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTimeStamps() {
        return TimeStamps;
    }

    public void setTimeStamps(String timeStamps) {
        TimeStamps = timeStamps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Title);
        dest.writeString(Content);
        dest.writeString(TimeStamps);
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id=" + id +
                ", Title='" + Title + '\'' +
                ", Content='" + Content + '\'' +
                ", TimeStamps='" + TimeStamps + '\'' +
                '}';
    }
}
