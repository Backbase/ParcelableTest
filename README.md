# ParcelableTest
A library making it easy to verify that your `Parcelable` implementations behave as expected when they are parceled and un-parceled.

## Usage
Extend `ParcelableTest` and provide an instance of your `Parcelable` as well as its `CREATOR`:
```java
public class MyParcelableTest extends ParcelableTest<MyParcelable> {

    @NonNull
    @Override
    protected MyParcelable newItem() {
        return new MyParcelable("Member variable value");
    }

    @NonNull
    @Override
    protected Parcelable.Creator<MyParcelable> itemCreator() {
        return MyParcelable.CREATOR;
    }
}
```

When you run `MyParcelableTest`, a test will parcel your provided instance and un-parcel it, and then assert that the 2 objects are equal before and
after.
