CKEDITOR.editorConfig = function( config ) {
    config.toolbarGroups = [
        { name: 'styles', groups: [ 'styles' ] },
        { name: 'colors', groups: [ 'colors' ] },
        { name: 'basicstyles', groups: [ 'cleanup', 'basicstyles' ] },
        { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
        { name: 'insert', groups: [ 'insert' ] },
        { name: 'links', groups: [ 'links' ] },
        { name: 'document', groups: [ 'document', 'mode' ] },
        { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
        { name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
        { name: 'forms', groups: [ 'forms' ] },
        { name: 'tools', groups: [ 'tools' ] },
        { name: 'others', groups: [ 'others' ] },
        { name: 'about', groups: [ 'about' ] }
    ];

    config.removeButtons = 'About,ShowBlocks,Format,Styles,PageBreak,Language,BidiRtl,BidiLtr,CreateDiv,Blockquote,HiddenField,Textarea,TextField,ImageButton,Form,Scayt,SelectAll,Save,NewPage,Print,Cut,Copy,Paste,Checkbox,Radio,Select,Button,Indent,Outdent';
    config.extraPlugins = 'filebrowser';
};