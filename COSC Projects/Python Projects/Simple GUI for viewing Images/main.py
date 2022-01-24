import PySimpleGUI
import PySimpleGUI as gui
import os.path

# two column layout for window

file_list_column = [
    [
        gui.Text("Image Folder"),
        gui.In(size=(25, 1), enable_events=True, key="-FOLDER-"),
        gui.FolderBrowse(),
    ],
    [
        gui.Listbox(
            values=[], enable_events=True, size=(40, 20), key="-FILE LIST-"
        )
    ],
]

# Showing the name of the file that was chosen
image_viewer_column = [
    [gui.Text("Chose an image from the list on the left:")],
    [gui.Text(size=(40, 1), key="-TOUT-")],
    [gui.Image(key="-IMAGE-")],
]

# Full layout

layout = \
    [
        [
            gui.Column(file_list_column),
            gui.VSeparator(),
            gui.Column(image_viewer_column),
        ]
    ]

window = gui.Window("Image Viewer", layout)

# Running the event loop

while True:
    event, values = window.read()
    if event == "Exit" or event == PySimpleGUI.WIN_CLOSED:
        break
    # Folder name was filled in, make a list of files in the folder
    if event == "-FOLDER-":
        folder = values["-FOLDER-"]
        try:
            # getting list of files in folder
            file_list = os.listdir(folder)
        except:
            file_list = []

        fnames = [
            f
            for f in file_list
            if os.path.isfile(os.path.join(folder, f))
               and f.lower().endswith((".png", ".gif"))
        ]
        window["-FILE LIST-"].update(fnames)
    # used if a file was chosen from the list
    elif event == "-FILE LIST-":
        try:
            filename = os.path.join(
                values["-FOLDER-"], values["-FILE LIST-"][0]
            )
            window["-TOUT-"].update(filename)
            window["-IMAGE-"].update(filename=filename)
        except:
            pass

window.close()
