package io.zeebe.zeeqs.data.resolvers

import graphql.kickstart.tools.GraphQLQueryResolver
import io.zeebe.zeeqs.data.entity.WorkflowInstance
import io.zeebe.zeeqs.data.entity.WorkflowInstanceState
import io.zeebe.zeeqs.data.repository.WorkflowInstanceRepository
import io.zeebe.zeeqs.graphql.resolvers.connection.WorkflowInstanceConnection
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class WorkflowInstanceQueryResolver(
        val workflowInstanceRepository: WorkflowInstanceRepository
) : GraphQLQueryResolver {

    fun workflowInstances(limit: Int, page: Int, stateIn: List<WorkflowInstanceState>): WorkflowInstanceConnection {
        return WorkflowInstanceConnection(
                getItems = { workflowInstanceRepository.findByStateIn(stateIn, PageRequest.of(page, limit)).toList() },
                getCount = { workflowInstanceRepository.countByStateIn(stateIn) }
        )
    }

    fun workflowInstance(key: Long): WorkflowInstance? {
        return workflowInstanceRepository.findByIdOrNull(key)
    }

}