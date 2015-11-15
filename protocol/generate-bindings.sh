#!/bin/bash

cd "$(dirname "${0}")"

files=(
    v3/file_chmod.fbs
    v3/file_close.fbs
    v3/file_open.fbs
    v3/file_read.fbs
    v3/file_seek.fbs
    v3/file_stat.fbs
    v3/file_write.fbs
    v3/file_selinux_get_label.fbs
    v3/file_selinux_set_label.fbs
    v3/path_chmod.fbs
    v3/path_copy.fbs
    v3/path_selinux_get_label.fbs
    v3/path_selinux_set_label.fbs
    v3/mb_get_version.fbs
    v3/mb_get_installed_roms.fbs
    v3/mb_get_booted_rom_id.fbs
    v3/mb_switch_rom.fbs
    v3/mb_set_kernel.fbs
    v3/mb_wipe_rom.fbs
    v3/reboot.fbs
    request.fbs
    response.fbs
)

mkdir -p ../mbtool/protocol

flatc -c -o ../mbtool/protocol "${files[@]}"
flatc -j -o ../Android_GUI/src "${files[@]}"
