
# Antonio
Android library for the adapter view (RecyclerView, ViewPager, ViewPager2)

* Free from implementation of the adapter's boilerplate code !
* Reuse view dependencies and Isolate view logic!
* Manage the recycler view state on your view model!
* Implement nested recycler view or pager efficiently!
* You don't need to learn anything for new theories if you already know about adapter views (RecyclerView, ViewPager, ViewPager2)!

```
                 _              _       
     /\         | |            (_)      
    /  \   _ __ | |_ ___  _ __  _  ___  
   / /\ \ | '_ \| __/ _ \| '_ \| |/ _ \ 
  / ____ \| | | | || (_) | | | | | (_) |
 /_/    \_\_| |_|\__\___/|_| |_|_|\___/ 
```     
# Install
## Without data binding
```groovy
dependencies {
    def antonioVersion = '1.0.1-alpha'
    def antonioAnnotationVersion = '0.0.1-alpha'
  
    implementation "io.github.naverz:antonio:$antonioVersion"

    implementation "io.github.naverz:antonio-annotation:$antonioAnnotationVersion"

    //For java
    annotationProcessor "io.github.naverz:antonio-compiler:$antonioAnnotationVersion"

    //For kotlin
    kapt "io.github.naverz:antonio-annotation:$antonioAnnotationVersion"

    //For paging2
    implementation "io.github.naverz:antonio-paging2:$antonioVersion"

    //For paging3
    implementation "io.github.naverz:antonio-paging3:$antonioVersion"
}
```
## With data binding
```groovy
dependencies {
    def antonioVersion = '1.0.1-alpha'
    def antonioAnnotationVersion = '0.0.1-alpha'
  
    implementation "io.github.naverz:antonio-databinding:$antonioVersion"

    implementation "io.github.naverz:antonio-annotation:$antonioAnnotationVersion"

    //For java
    annotationProcessor "io.github.naverz:antonio-compiler:$antonioAnnotationVersion"

    //For kotlin
    kapt "io.github.naverz:antonio-annotation:$antonioAnnotationVersion"

    //For paging2
    implementation "io.github.naverz:antonio-databinding-paging2:$antonioVersion"

    //For paging3
    implementation "io.github.naverz:antonio-databinding-paging3:$antonioVersion'
}
```

# Basic Usage
There are four steps for RecyclerView (ViewPager, ViewPager2 also supported)

  1. Implement `AntonioModel` to bind model on your view holder.
  <p align="center">
  <img src ="https://user-images.githubusercontent.com/15243641/134372911-2c71d7b8-9abb-4757-8991-46b6a3057763.png" width ="90">
 </p>

  ```kotlin
  data class ContentSmallModel(
    val id: String,
    @DrawableRes val iconRes: Int,
    val price: Int,
    val onClick: (id: String) -> Unit,
    val onLongClick: (id: String) -> Boolean,
    val selectedIds: LiveData<Set<String>>
  ):AntonioModel
  ```
  2. Implement `TypedViewHolder`. (You can skip this step [with Antonio data binding](#with-data-binding))
  ```kotlin
  class SmallContentViewHolder(parent: ViewGroup) :
    TypedViewHolder<ContentSmallModel>(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_content_small, parent, false)
    ) {
    init {
        // onCreateView process
    }

    override fun onBindViewHolder(data: ContentSmallModel, position: Int, payloads: List<Any>?) {
        super.onBindViewHolder(data, position, payloads)
        // onBindViewHolder process
    }

    override fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(viewHolder)
    }

    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(viewHolder)
    }

    override fun onViewRecycled() {
        super.onViewRecycled()
    }
}
  ```
  3. Link `AntonioModel` and `TypedViewHolder` with `DependencyBuilder` on the global container. (You can skip this step [with Antonio Annotation](#with-antonio-annotation))
  ```kotlin
    private fun linkAntonio() {
        AntonioSettings.viewHolderContainer.add(
            ContentSmallModel::class.java,
            ViewHolderBuilder { parent ->
                return@ViewHolderBuilder SmallContentViewHolder(parent)
            })
    }
  
  ```
  4. Declare `AntonioAdapter` (or `AntonioListAdapter`) and Set adapter with your data to RecyclerView.
  ```kotlin
   private fun initAdapter(){
       // You also can specify the type of Antonio model for the adapter, if you don't need various view types.
       // e.g., AntonioAdapter<ContentSmallModel>()
        binding.recyclerView.adapter =
            AntonioAdapter<AntonioModel>().apply { currentList = viewModel.antonioModels }
        viewModel.onDataSetChanged.observe(this) {
            binding.recyclerView.adapter?.notifyDataSetChanged()
        }
   }
  
  ```
  <p align="center">
  <img src ="https://user-images.githubusercontent.com/15243641/134373020-e7b1f23d-1b10-406c-b180-63acdcb27215.png" width ="200">
 </p>

# With Antonio Annotation
You can skip linking `AntonioModel` and `View dependency` with `antonio-annotations`

## How to use

Declare `MappedWithViewDependency` annotation on your `AntonioModel` class
```kotlin
@MappedWithViewDependency(viewClass = SmallContentViewHolder::class)
data class ContentSmallModel(
    val id: String,
    @DrawableRes val iconRes: Int,
    val price: Int,
    val onClick: (id: String) -> Unit,
    val onLongClick: (id: String) -> Boolean,
    val selectedIds: LiveData<Set<String>>
) : AntonioModel 
```
Write `AntonioAnnotation.init` on the place where the app is initialized. (e.g., Application.onCreate)
```kotlin
AntonioAnnotation.init()
```
## Preconditions for the annotation
* Only `TypedViewHolder`, `PagerViewDependency`, `AntonioFragment` are supported.
  ```kotlin
    @MappedWithViewDependency(
        // It can use the class that is implemented by one of the three classes above only.
        viewClass = SmallContentViewHolder::class)

  ```
* ViewHolder's constructor must have only one viewGroup parameter.
  * e.g., 
    ```kotlin 
    class SmallContentViewHolder(
        // Require view group only for the constructor's parameter.
        parent: ViewGroup
    ) :
        TypedViewHolder<ContentSmallModel>(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_content_small, parent, false)
        ) {
    }
    ```
* PagerViewDependency constructor, Fragment constructor must not have any parameters.
* If you need additional parameters for the view dependency constructor, You can link manually like [Basic Usage, Step 3](#basic-usage) 



# With Data Binding
**We strongly recommend using Antonio with DataBinding.**

You don't need to implement `ViewHolder` class with `antonio-databinding`.\
Just Implement the layout xml file, then Antonio will bind the model automatically at `onBindViewHolder` point.

## How to use
Implement `AntonioBindingModel` to bind model on your **view holder xml**. 
```kotlin
data class ContentSmallModel(
    val id: String,
    @DrawableRes val iconRes: Int,
    val price: Int,
    val onClick: (id: String) -> Unit,
    val onLongClick: (id: String) -> Boolean,
    val selectedIds: LiveData<Set<String>>
) : AntonioBindingModel {
    // Layout id to be inflated
    override fun layoutId(): Int = R.layout.view_holder_content_small
    // Variable id in XML to be bind every time when the view is attached
    // (e.g., onBindViewHolder in ViewHolder, onViewCreated in Fragment).
    override fun bindingVariableId(): Int = BR.model
}
```
`view_holder_content_small.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    >
    <data>
        <variable
            name="model"
            type="io.github.naverz.antonio.sample.ContentSmallModel"
        />
    </data>

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:gravity="center_horizontal"
        android:onClick="@{()->model.onClick.invoke(model.id)}"
        android:onLongClick="@{(view)->model.onLongClick.invoke(model.id)}"
        android:orientation="vertical"
        android:padding="5dp"
        >
        <!-- ... -->
    </LinearLayout>
</layout>
```
After writing the code above, Just use `ContentSmallModel` to AntonioAdapter. \
(It can be used with another `AntonioModel`, which you want to implement view holder class.)
## LifecycleOwner
LifecycleOwner, which is nearest from the view, will be automatically injected on All of XML inflated from `AutoBindingModel` in order to use LiveData for the one or two-way binding.

<p align="center">
<img src="https://user-images.githubusercontent.com/15243641/134387612-8217d441-6a1e-4e41-98f4-5368ca987890.png" width="300">
</p>

# Plug-in 
* ViewPager, ViewPager2 
* Paging 2,3
* RecyclerView state

# Sample
[The sample module](./sample) is available!

<img src="https://user-images.githubusercontent.com/15243641/134202280-4fb1787d-78a6-4316-9b9f-5778fcc68679.gif" width="270" />

# License
```
Antonio
Copyright (c) 2021-present NAVER Z Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

