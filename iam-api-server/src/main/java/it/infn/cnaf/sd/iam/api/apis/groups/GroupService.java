/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2020
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.infn.cnaf.sd.iam.api.apis.groups;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import it.infn.cnaf.sd.iam.persistence.entity.GroupEntity;

public interface GroupService {
  
  Optional<GroupEntity> findByName(String name);
  
  Optional<GroupEntity> findByUuid(String uuid);
  
  Page<GroupEntity> getGroups(Pageable page);
  
  GroupEntity createGroup(GroupEntity group);
  
  void deleteGroupByUuid(String uuid);
  
}
