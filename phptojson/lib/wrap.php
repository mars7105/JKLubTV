<?php
session_start ();
class Wrap {
	public function wrapGreyContent($h1, $body) {
		$wrap = '<div class="panel panel-default">' . "\n";
		$wrap .= '  <div class="panel-heading">' . "\n";
		$wrap .= '    <h1 class="panel-title">' . $h1 . '</h1>' . "\n";
		$wrap .= '	</div>' . "\n";
		$wrap .= '  <div class="panel-body">' . "\n";
		$wrap .= $body . "\n";
		$wrap .= '  </div>' . "\n";
		$wrap .= '</div>' . "\n";
		
		return $wrap;
	}
	public function wrapDarkBlueContent($h1, $body) {
		$wrap = '<div class="panel panel-primary ">' . "\n";
		$wrap .= '  <div class="panel-heading">' . "\n";
		$wrap .= '    <h1 class="panel-title">' . $h1 . '</h1>' . "\n";
		$wrap .= '	</div>' . "\n";
		$wrap .= '  <div class="panel-body">' . "\n";
		$wrap .= $body . "\n";
		$wrap .= '  </div>' . "\n";
		$wrap .= '</div>' . "\n";
		return $wrap;
	}
	public function wrapLightBlueContent($h1, $body) {
		$wrap = '<div class="panel panel-info ">' . "\n";
		$wrap .= '  <div class="panel-heading">' . "\n";
		$wrap .= '    <h1 class="panel-title">' . $h1 . '</h1>' . "\n";
		$wrap .= '	</div>' . "\n";
		$wrap .= '  <div class="panel-body">' . "\n";
		$wrap .= $body . "\n";
		$wrap .= '  </div>' . "\n";
		$wrap .= '</div>' . "\n";
		return $wrap;
	}
	public function wrapYellowContent($h1, $body) {
		$wrap = '<div class="panel panel-warning ">' . "\n";
		$wrap .= '  <div class="panel-heading">' . "\n";
		$wrap .= '    <h1 class="panel-title">' . $h1 . '</h1>' . "\n";
		$wrap .= '	</div>' . "\n";
		$wrap .= '  <div class="panel-body">' . "\n";
		$wrap .= $body . "\n";
		$wrap .= '  </div>' . "\n";
		$wrap .= '</div>' . "\n";
		return $wrap;
	}
	public function wrapRedContent($h1, $body) {
		$wrap = '<div class="panel panel-danger ">' . "\n";
		$wrap .= '  <div class="panel-heading">' . "\n";
		$wrap .= '    <h1 class="panel-title">' . $h1 . '</h1>' . "\n";
		$wrap .= '	</div>' . "\n";
		$wrap .= '  <div class="panel-body">' . "\n";
		$wrap .= $body . "\n";
		$wrap .= '  </div>' . "\n";
		$wrap .= '</div>' . "\n";
		return $wrap;
	}
	public function wrapGreenContent($h1, $body) {
		$wrap = '<div class="panel panel-success ">' . "\n";
		$wrap .= '  <div class="panel-heading">' . "\n";
		$wrap .= '    <h1 class="panel-title">' . $h1 . '</h1>' . "\n";
		$wrap .= '	</div>' . "\n";
		$wrap .= '  <div class="panel-body">' . "\n";
		$wrap .= $body . "\n";
		$wrap .= '  </div>' . "\n";
		$wrap .= '</div>' . "\n";
		return $wrap;
	}
	public function wrapNavigation($h1, $menuItem, $naviLinks) {
		$wrap = '   <!-- Fixed navbar -->
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <p class="navbar-brand">' . $h1 . '</p>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
			<li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">' . $menuItem . '<span class="caret"></span></a>
              <ul class="dropdown-menu">';
		$wrap .= $naviLinks . "\n";
		$wrap .= '    </ul>
            </li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>';
		
		return $wrap;
	}
	public function wrapSidebar($body) {
		$wrap = '<div class="col-sm-3 col-sm-offset-1 blog-sidebar">' . $body . '</div>';
		return $wrap;
	}
	public function wrapSidebarModule($body) {
		$wrap = '<div class="sidebar-module">' . $body . '</div>';
		return $wrap;
	}
}
?>