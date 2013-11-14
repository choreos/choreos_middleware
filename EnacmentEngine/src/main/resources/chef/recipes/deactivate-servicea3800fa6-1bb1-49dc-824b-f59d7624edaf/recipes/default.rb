#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

#ruby_block "disable-servicea3800fa6-1bb1-49dc-824b-f59d7624edaf" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicea3800fa6-1bb1-49dc-824b-f59d7624edaf::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a3800fa6-1bb1-49dc-824b-f59d7624edaf']['InstallationDir']}/service-a3800fa6-1bb1-49dc-824b-f59d7624edaf.jar]", :immediately
#end

ruby_block "remove-servicea3800fa6-1bb1-49dc-824b-f59d7624edaf" do
  block do
  	node.run_list.remove("recipe[servicea3800fa6-1bb1-49dc-824b-f59d7624edaf::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea3800fa6-1bb1-49dc-824b-f59d7624edaf::jar]") }
end

ruby_block "remove-deactivate-servicea3800fa6-1bb1-49dc-824b-f59d7624edaf" do
  block do
    node.run_list.remove("recipe[deactivate-servicea3800fa6-1bb1-49dc-824b-f59d7624edaf]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea3800fa6-1bb1-49dc-824b-f59d7624edaf]") }
end
