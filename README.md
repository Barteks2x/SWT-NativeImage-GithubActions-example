# SWT + Native Image + Github Actions example project

An example SWT project built using native image with github actions.
#### JNI config

There is probably quite a bit of unnecessary stuff in JNI config that would significantly reduce executable size if removed. If someone can make something that reliably finds what is actually ever accessed, it could be simplified. As it is, I just did the easiest thing that will work reliably, even outside of the minimal SWT GUI example case.

#### Files to check on version bump

`build.gradle`
`macos-app-files/swtexample.app/Contents/Info.plist`