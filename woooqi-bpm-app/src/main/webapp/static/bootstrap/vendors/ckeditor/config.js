/**
 * @license Copyright (c) 2003-2017, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.toolbarGroups = [
		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
		{ name: 'forms', groups: [ 'forms' ] },
		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
		{ name: 'links', groups: [ 'links' ] },
		{ name: 'insert', groups: [ 'insert' ] },
		{ name: 'styles', groups: [ 'styles' ] },
		{ name: 'colors', groups: [ 'colors' ] },
		{ name: 'tools', groups: [ 'tools' ] },
		{ name: 'others', groups: [ 'others' ] },
		{ name: 'about', groups: [ 'about' ] }
	];

	config.removeButtons = 'About,Maximize,ShowBlocks,Image,Flash,PageBreak,Iframe,Link,Unlink,Anchor,Language,BidiRtl,BidiLtr,Blockquote,CreateDiv,Outdent,Indent,Print,Source,Save,NewPage,Templates,PasteText,PasteFromWord,Scayt,CopyFormatting,RemoveFormat,ImageButton,Smiley,Styles,Format,Font';
	config.removePlugins = 'elementspath';
	config.wordcount = {
	        showParagraphs: false, 
	        showWordCount: false, 
	        showCharCount: true, 
	        countSpacesAsChars: false,
	        countHTML: false,
	        maxWordCount: -1,
	        maxCharCount: 500,
	        filter: new CKEDITOR.htmlParser.filter({
	            elements: {
	                div: function( element ) {
	                    if(element.attributes.class == 'mediaembed') {
	                        return false;
	                    }
	                }
	            }
	        })
	    };
	    
	    //添加中文字体
	    config.font_names="宋体/SimSun;新宋体/NSimSun;仿宋_GB2312/FangSong_GB2312;楷体_GB2312/KaiTi_GB2312;黑体/SimHei;微软雅黑/Microsoft YaHei;幼圆/YouYuan;华文彩云/STCaiyun;华文行楷/STXingkai;方正舒体/FZShuTi;方正姚体/FZYaoti;"+ config.font_names;
};
