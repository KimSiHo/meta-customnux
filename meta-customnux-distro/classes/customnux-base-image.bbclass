inherit core-image

IMAGE_FSTYPES = " tar.bz2 ext4 ext4.gz wic wic.gz wic.bmap"
IMAGE_FSTYPES:qemuarm64-custom = " tar.bz2 ext4 ext4.gz"

IMAGE_ROOTFS_SIZE = "20480"
IMAGE_ROOTFS_EXTRA_SPACE = "20480"
IMAGE_ROOTFS_ALIGNMENT = "1024"
