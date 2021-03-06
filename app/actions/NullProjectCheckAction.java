/**
 * Yobi, Project Hosting SW
 *
 * Copyright 2013 NAVER Corp.
 * http://yobi.io
 *
 * @Author Keesun Baik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package actions;

import actions.support.PathParser;
import models.Project;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import utils.AccessLogger;
import utils.ErrorViews;

/**
 * /{user.loginId}/{project.name}/** 패턴의 요청에 해당하는 프로젝트가 존재하는지 확인하는 액션.
 * - URL에 해당하는 프로젝트가 없을 때 404 Not Found로 응답한다.
 * - URL에 해당하는 프로젝트가 있을 때 요청 처리한다.
 *
 * @author Keesun Baik
 */
public class NullProjectCheckAction extends Action.Simple {

    @Override
    public Result call(Http.Context context) throws Throwable {
        PathParser parser = new PathParser(context);
        String ownerLoginId = parser.getOwnerLoginId();
        String projectName = parser.getProjectName();

        Project project = Project.findByOwnerAndProjectName(ownerLoginId, projectName);

        if (project == null) {
            return AccessLogger.log(context.request(), notFound(ErrorViews.NotFound.render("error.notfound.project"))
                    , null);
        }

        return this.delegate.call(context);
    }
}
